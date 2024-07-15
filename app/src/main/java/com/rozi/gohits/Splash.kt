package com.rozi.gohits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val timer: Thread = object : Thread() {
            override fun run() {
                try {
                    //berapalama splashscreen akan ditampilkan dalam milisecond
                    sleep(5000)  //---5 detik delay
                } catch (e: InterruptedException) {
                    // TODO: handle exception
                    e.printStackTrace()
                } finally {
                    //activity yang akan dijalankan setelah splashscreen selesai
                    finish()
                    intent = Intent(this@Splash, Login::class.java)
                    startActivity(intent)
                }
            }
        }
        timer.start()
    }
}