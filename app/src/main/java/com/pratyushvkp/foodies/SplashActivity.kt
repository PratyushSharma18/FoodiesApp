package com.pratyushvkp.foodies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.ActionBar

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //It will hide the Headtitle actionBar.

        val actionBar:ActionBar? = supportActionBar
        actionBar?.hide()

// It will intent from SplashActivity to MainActivity after postDelay time interval stated ( 2 sec )
        Handler().postDelayed(

            {
                startActivity( Intent(this,MainActivity::class.java))
                finish()
            }
             , 2000 )
    }
}