package com.muhasib.progemanag.activities

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


import com.muhasib.progemanag.R
import com.muhasib.progemanag.firebase.FirestoreClass
import com.muhasib.progemanag.models.User

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_sign_up_activity)
        setupActionBar(toolbar)

        var SingUpbtn=findViewById<Button>(R.id.btn_sign_up)
        SingUpbtn.setOnClickListener {
            registerUser()
        }
    }
    fun userRegisteredSuccess(){

        Toast.makeText(this," You have successfully registered",Toast.LENGTH_LONG).show()

        hideProgrssDialog()
        FirebaseAuth.getInstance().signOut()
        finish()

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

    private fun registerUser() {
        val et_name = findViewById<EditText>(R.id.et_name)
        val et_email = findViewById<EditText>(R.id.et_email)
        val et_password = findViewById<EditText>(R.id.et_password)

        val name = et_name.text.toString().trim { it <= ' ' }
        val password = et_password.text.toString().trim { it <= ' ' }
        val email = et_email.text.toString().trim { it <= ' ' }

        if (validateForm(name, email, password)) {
            showProgressDialog(resources.getString(R.string.please_wait))

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    hideProgrssDialog()  // Hide progress dialog here

                    if (task.isSuccessful) {
                        val firebaseUser = task.result?.user

                        if (firebaseUser != null) {
                            val registeredEmail = firebaseUser.email!!
                            val user = User(firebaseUser.uid, name, registeredEmail)

                            FirestoreClass().registerUser(this, user)
                        } else {
                            Toast.makeText(this, "Registration failed: User data not available", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun validateForm(name :String, email :String, password:String) :Boolean{

        return when{
            TextUtils.isEmpty(name)->{
                showErrorSnackBar("Please Enter a name")
                false
            }
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