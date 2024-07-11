package com.rozi.gohits.cert

import android.content.Context
import com.rozi.gohits.R
import okhttp3.OkHttpClient
import java.io.BufferedInputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

fun getUnsafeOkHttpClient(context: Context): OkHttpClient {
    return try {
        val cf = CertificateFactory.getInstance("X.509")

        // Load CA from an InputStream
        val caInput = BufferedInputStream(context.resources.openRawResource(R.raw.gohit))
        val ca: X509Certificate = caInput.use { cf.generateCertificate(it) as X509Certificate }

        // Create a KeyStore containing our trusted CAs
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType).apply {
            load(null, null)
            setCertificateEntry("ca", ca)
        }

        // Create a TrustManager that trusts the CAs in our KeyStore
        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val tmf = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
            init(keyStore)
        }

        // Create an SSLContext that uses our TrustManager
        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, tmf.trustManagers, null)
        }

        // Create an OkHttpClient that uses our SSLContext
        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslContext.socketFactory, tmf.trustManagers[0] as X509TrustManager)
        builder.build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}
