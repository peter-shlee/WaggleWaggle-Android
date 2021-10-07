package com.somasoma.wagglewaggle.data.repository_impl.firebase_repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.somasoma.wagglewaggle.data.model.TmpAvatars
import com.somasoma.wagglewaggle.domain.repository.AvatarsRepository
import timber.log.Timber
import javax.inject.Inject

class FirebaseAvatarsRepository @Inject constructor() : AvatarsRepository {
    companion object {
        private const val FIREBASE_URL =
            "https://speak-world-default-rtdb.asia-southeast1.firebasedatabase.app"
        private const val AVATARS = "avatars"
    }

    private var database = FirebaseDatabase.getInstance(FIREBASE_URL).reference

    override fun getAvatars(callback: (TmpAvatars) -> Unit) {
        val userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Timber.d(snapshot.value.toString())
                val avatars: TmpAvatars? = snapshot.getValue(TmpAvatars::class.java)
                avatars?.let(callback)
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.d(error.toString())
            }
        }
        database.child(AVATARS).addListenerForSingleValueEvent(userListener)
    }
}