package com.muhasib.progemanag.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.muhasib.progemanag.R

class SignInActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        auth=FirebaseAuth.getInstance()


        var toolbar=findViewById<Toolbar>(R.id.toolbar_sign_in_activity)
        var btn_SignIn= findViewById<Button>(R.id.btn_sign_in)
         btn_SignIn.setOnClickListener {
             signInRegisteredUser()
         }
        setupActionBar(toolbar)


    }
    private fun setupActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawable_ic_black_color_back_24dp)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun signInRegisteredUser() {
        val email = findViewById<EditText>(R.id.et_email_signIn).text.toString().trim { it <= ' ' }
        val password = findViewById<EditText>(R.id.et_password_signIn).text.toString().trim { it <= ' ' }

        if (validateForm(email, password)) {
            showProgressDialog(resources.getString(R.string.please_wait))

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    hideProgrssDialog()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Sign In", "signInWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this, MainActivity::class.java))

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Sign In", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }
        }
    }
    private fun validateForm( email :String, password:String) :Boolean{

        return when{

            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please Enter a email")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Please Enter a password")
                false
            }

            else -> {true}
        }
    }
}