import android.content.Context
import com.rozi.gohits.cert.getUnsafeOkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit: Retrofit? = null

    fun getClient(context: Context): Retrofit {
        if (retrofit == null) {
            val client = getUnsafeOkHttpClient(context)

            retrofit = Retrofit.Builder()
                .baseUrl("https://gohit.id/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    val instance: Retrofit
        get() = retrofit ?: throw IllegalStateException("ApiClient not initialized")
}
