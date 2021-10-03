package com.somasoma.wagglewaggle.core.repository.firebase_repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.somasoma.wagglewaggle.core.model.Avatars
import com.somasoma.wagglewaggle.core.repository.AvatarsRepository
import timber.log.Timber
import javax.inject.Inject

class FirebaseAvatarsRepository @Inject constructor() : AvatarsRepository {
    companion object {
        private const val FIREBASE_URL =
            "https://speak-world-default-rtdb.asia-southeast1.firebasedatabase.app"
        private const val AVATARS = "avatars"
    }

    private var database = FirebaseDatabase.getInstance(FIREBASE_URL).reference

    override fun getAvatars(callback: (Avatars) -> Unit) {
        val userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Timber.d(snapshot.value.toString())
                val avatars: Avatars? = snapshot.getValue(Avatars::class.java)
                avatars?.let(callback)
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.d(error.toString())
            }
        }
        database.child(AVATARS).addListenerForSingleValueEvent(userListener)
    }
}