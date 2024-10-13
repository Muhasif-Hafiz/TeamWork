package com.muhasib.progemanag.activities

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.muhasib.progemanag.R

open class BaseActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    fun showProgressDialog(text: String) {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setContentView(R.layout.dialog_progress)

        val progress = mProgressDialog.findViewById<TextView>(R.id.tv_progress_text)
        progress.text = text

        mProgressDialog.show()
    }

    fun hideProgressDialog() {  // Fixed typo here
        mProgressDialog.dismiss()
    }

    fun getCurrentUserId(): String {  // Fixed typo here
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun doubleBackToPress() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(
            this,
            resources.getString(R.string.please_click_back_again_to_exit),
            Toast.LENGTH_SHORT
        ).show()


        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    fun showErrorSnackBar(message: String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.snack_bar_color))
        snackbar.show()
    }
}
