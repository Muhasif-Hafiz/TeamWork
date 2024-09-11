package com.muhasib.progemanag.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.muhasib.progemanag.activities.SignUpActivity
import com.muhasib.progemanag.models.User
import com.muhasib.progemanag.utils.Constants

class FirestoreClass {

    private val mFirestore=FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo : User){

        mFirestore.collection(Constants.USERS).document(getCurrentUserId()).set(userInfo,
            SetOptions.mergeFields()).addOnSuccessListener {
                activity.userRegisteredSuccess()
        }

    }
    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var CurrentUserID=""
        if(currentUser!=null){
            CurrentUserID=currentUser.uid
        }
        return CurrentUserID
    }
}