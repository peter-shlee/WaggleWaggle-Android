package com.somasoma.speakworld.core.repository.firebase_repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.somasoma.speakworld.core.model.User
import com.somasoma.speakworld.core.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

class FirebaseUserRepository @Inject constructor() : UserRepository {
    companion object {
        private const val FIREBASE_URL =
            "https://speak-world-default-rtdb.asia-southeast1.firebasedatabase.app"
    }

    private val uid = FirebaseAuth.getInstance().currentUser?.uid
    private var database = FirebaseDatabase.getInstance(FIREBASE_URL).reference

    override fun postUser(user: User, onSuccessCallback: () -> Unit) {
        uid?.let {
            database.child("users").child(it).setValue(user).addOnSuccessListener { onSuccessCallback() }
        }
    }

    override fun getUser(callback: (User) -> Unit) {
        uid?.let {
            val userListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Timber.d(snapshot.value.toString())
                    val user = snapshot.getValue(User::class.java)
                    user?.let(callback)
                    Timber.d(user.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    Timber.d(error.toString())
                }
            }

            database.child("users").child(it).addValueEventListener(userListener)
        }
    }

    override fun deleteUser(onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit) {
        uid?.let {
            database.child("users").child(it).removeValue().addOnSuccessListener {
                onSuccessCallback()
            }.addOnFailureListener {
                onFailureCallback()
            }
        }
    }

    override fun setUserName(name: String, onSuccessCallback: () -> Unit) {
        uid?.let {
            database.child("users").child(it).child("name").setValue(name).addOnSuccessListener { onSuccessCallback() }
        }
    }

    override fun setUserLanguage(language: String, onSuccessCallback: () -> Unit) {
        uid?.let {
            database.child("users").child(it).child("language").setValue(language).addOnSuccessListener { onSuccessCallback() }
        }
    }
}