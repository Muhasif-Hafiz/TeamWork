package com.muhasib.progemanag

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)


         val typeFace : Typeface= Typeface.createFromAsset(assets, "carbon bl.ttf")

        var appname= findViewById<TextView>(R.id.tv_app_name)
        appname.typeface=typeFace

        Handler().postDelayed({

startActivity(Intent(this,IntroActivity::class.java))
            finish()
        },2500)
    }
}