package com.muhasib.progemanag.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.muhasib.progemanag.R


import com.projemanag.firebase.FirestoreClass

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)


         val typeFace : Typeface= Typeface.createFromAsset(assets, "carbon bl.ttf")

        var appname= findViewById<TextView>(R.id.tv_app_name)
        appname.typeface=typeFace

        Handler().postDelayed({

            var currentUserId= FirestoreClass().getCurrentUserID()


            if (currentUserId != null) {
                if(currentUserId.isNotEmpty()){
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }else{
                startActivity(Intent(this, IntroActivity::class.java))
            }

            finish()
        },2500)
    }
}