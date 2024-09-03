package com.muhasib.progemanag.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.muhasib.progemanag.R

class IntroActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)

       var SignUp=findViewById<Button>(R.id.btn_sign_up_intro)
        var SignIn= findViewById<Button>(R.id.btn_sign_in_intro)

        SignUp.setOnClickListener {


            var intent= Intent(this, SignUpActivity::class.java)

            startActivity(intent)

        }
        SignIn.setOnClickListener {
            var intent= Intent(this, SignUpActivity::class.java)

            startActivity(intent)
        }

    }
}