package com.somasoma.speakworld

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import timber.log.Timber

object FirebaseRealtimeDB : RemoteDataSource {

    private const val FIREBASE_URL =
        "https://speak-world-default-rtdb.asia-southeast1.firebasedatabase.app"

    private var database = FirebaseDatabase.getInstance(FIREBASE_URL).reference

    override fun writeNewUser(userId: String, name: String, language: String) {
        val user = User(name, language)

        database.child("users").child(userId).setValue(user)
    }

    override fun getUser(userId: String, callback: (User) -> Unit) {
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
        database.child("users").child(userId).addListenerForSingleValueEvent(userListener)
    }

    override fun setUserName(userId: String, newName: String) {
        database.child("users").child(userId).child("name").setValue(newName)
    }

    override fun setUserLanguage(userId: String, newLanguage: String) {
        database.child("users").child(userId).child("language").setValue(newLanguage)
    }
}