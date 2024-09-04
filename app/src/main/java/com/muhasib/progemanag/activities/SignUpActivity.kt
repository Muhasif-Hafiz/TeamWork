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

    private fun setupActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawable_ic_black_color_back_24dp)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun registerUser(){
        var et_name= findViewById<EditText>(R.id.et_name)
        var et_email= findViewById<EditText>(R.id.et_email)
        var et_password= findViewById<EditText>(R.id.et_password)

        var Name= et_name.text.toString().trim{it<=' '}
        var Password= et_password.text.toString().trim{it<=' '}
        var Email= et_email.text.toString().trim{it<=' '}

        if(validateForm(Name, Email, Password)){

           showProgressDialog(resources.getString(R.string.please_wait))

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email,Password).addOnCompleteListener {


                task->
                hideProgrssDialog()

                if(task.isSuccessful){
                    val fireBaseUser : FirebaseUser=task.result!!.user!!

                    val registeredEmail=fireBaseUser.email!!
                    Toast.makeText(this,"$Name you have successfully registered with email address $registeredEmail",Toast.LENGTH_LONG).show()

                    FirebaseAuth.getInstance().signOut()
                    finish()
                }else{
                    Toast.makeText(this,task.exception!!.message,Toast.LENGTH_SHORT).show()
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