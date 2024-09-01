package com.muhasib.progemanag

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IntroActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)

       var SignUp=findViewById<Button>(R.id.btn_sign_up_intro)

        SignUp.setOnClickListener {


            var intent= Intent(this, SignUpActivity::class.java)

            startActivity(intent)

        }

    }
}