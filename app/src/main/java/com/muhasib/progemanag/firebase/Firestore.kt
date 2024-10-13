package com.projemanag.firebase

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.muhasib.progemanag.activities.MainActivity
import com.muhasib.progemanag.activities.MyProfileActivity
import com.muhasib.progemanag.activities.SignInActivity
import com.muhasib.progemanag.activities.SignUpActivity
import com.muhasib.progemanag.models.User
import com.muhasib.progemanag.utils.Constants



/**
 * A custom class where we will add the operations performed for the Firestore database.
 */
class FirestoreClass {

    // Create an instance of Firebase Firestore
    private val mFireStore = FirebaseFirestore.getInstance()

    /**
     * A function to make an entry of the registered user in the Firestore database.
     */
    fun registerUser(activity: SignUpActivity, userInfo: User) {

        getCurrentUserID()?.let {
            mFireStore.collection(Constants.USERS)
                // Document ID for users fields. Here the document is the User ID.
                .document(it)
                // Here the userInfo are Fields and the SetOption is set to merge. It is for if you want to merge
                .set(userInfo, SetOptions.merge())
                .addOnSuccessListener {
                    // Here call a function of base activity for transferring the result to it.
                    activity.userRegisteredSuccess()
                }
                .addOnFailureListener { e ->
                    Log.e(
                        activity.javaClass.simpleName,
                        "Error writing document",
                        e
                    )
                }
        }
    }

    /**
     * A function to SignIn using Firebase and get the user details from Firestore Database.
     */
    fun loadUserData(activity: Activity) {

        getCurrentUserID()?.let {
            mFireStore.collection(Constants.USERS)
                // The document id to get the Fields of user.
                .document(it)
                .get()
                .addOnSuccessListener { document ->

                    val loggedInUser = document.toObject(User::class.java)
                    if (loggedInUser != null) {
                        when(activity){
                            is SignInActivity -> activity.signInSuccess(loggedInUser)
                            is MainActivity -> activity.updateNavigationUserDetails(loggedInUser)
                            is MyProfileActivity -> activity.setUserDataInUI(loggedInUser)
                            else -> Log.e("FirestoreClass", "Unhandled activity type")
                        }
                    } else {
                        Log.e("FirestoreClass", "Failed to convert document to User object")
                        // Optionally, notify the activity about the failure
                    }

                }
                .addOnFailureListener { e ->

                    when(activity){
                        is SignInActivity ->{
                            activity.hideProgressDialog()
                        }

                        is MainActivity ->{
                            activity.hideProgressDialog()
                        }

                        is MyProfileActivity -> {
                            activity.hideProgressDialog()
                        }
                    }

                }
        }
    }

    /**
     * A function for getting the user ID of the currently logged-in user.
     */
    fun getCurrentUserID(): String? {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser?.uid
    }
}
