package com.muhasib.progemanag.activities

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.muhasib.progemanag.R

open class BaseActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce=false
    lateinit var mProgressDialog : Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_base)

    }
    fun showProgressDialog(text :String){

        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.dialog_progress)

        val progress = mProgressDialog.findViewById<TextView>(R.id.tv_progress_text)
        progress.text = text

        mProgressDialog.show()
    }
    fun hideProgrssDialog(){
        mProgressDialog.dismiss()
    }

    fun fetCurrentUserId() :String{

        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun DoubleBackToPress(){
        if(doubleBackToExitPressedOnce){
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce=true

        Toast.makeText(this, resources.getString(R.string.please_click_back_again_to_exit), Toast.LENGTH_SHORT).show()
         Handler().postDelayed({doubleBackToExitPressedOnce=false},2000)
    }

    fun showErrorSnackBar(message : String){
        val snackbar=Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)

        val snackbarview= snackbar.view
        snackbarview.setBackgroundColor(ContextCompat.getColor(this,R.color.snack_bar_color))
        snackbar.show()
    }
}