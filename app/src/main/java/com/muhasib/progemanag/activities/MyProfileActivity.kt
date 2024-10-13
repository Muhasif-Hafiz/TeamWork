package com.muhasib.progemanag.activities

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.muhasib.progemanag.R
import com.muhasib.progemanag.models.User
import com.projemanag.firebase.FirestoreClass
import de.hdodenhof.circleimageview.CircleImageView

class MyProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_profile)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_my_profile_activity)
        setUpActionBar(toolbar)

        FirestoreClass().loadUserData(this)
    }

    private fun setUpActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
            title = resources.getString(R.string.my_profile)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun setUserDataInUI(user: User) {
        val ivUserImage = findViewById<CircleImageView>(R.id.iv_user_image)
        val etName = findViewById<EditText>(R.id.et_name)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etMobile = findViewById<EditText>(R.id.et_mobile)

        Glide.with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(ivUserImage)

        etName.setText(user.name)
        etEmail.setText(user.email)
        etMobile.setText(user.mobile.toString())
    }
}
