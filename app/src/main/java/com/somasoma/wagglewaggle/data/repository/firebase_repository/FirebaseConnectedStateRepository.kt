package com.somasoma.wagglewaggle.data.repository.firebase_repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import timber.log.Timber
import javax.inject.Inject

class FirebaseConnectedStateRepository @Inject constructor() {
    companion object {
        private const val FIREBASE_URL =
            "https://speak-world-default-rtdb.asia-southeast1.firebasedatabase.app"
        private const val USER_CONNECTED_STATE_PATH = ".info/connected"
        private const val CONNECTED_USERS_PATH = "connectedUsers"
    }

    private var userConnectedStateRepository =
        FirebaseDatabase.getInstance(FIREBASE_URL).getReference(USER_CONNECTED_STATE_PATH)
    private var connectedUsersRepository =
        FirebaseDatabase.getInstance(FIREBASE_URL).getReference(CONNECTED_USERS_PATH)

    fun registerOnUserConnectedStateCallback(
        onFailureCallback: () -> Unit
    ) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        userConnectedStateRepository.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java)
                if (connected == true) {
                    uid?.let {
                        connectedUsersRepository.child(it).setValue(true)
                        connectedUsersRepository.child(it).onDisconnect().removeValue()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.e(error.toString())
                onFailureCallback()
            }

        })
    }

    fun postCurrentUserOnline() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        uid?.let {
            connectedUsersRepository.child(it).setValue(true)
            connectedUsersRepository.child(it).onDisconnect().removeValue()
        }
    }

    fun postCurrentUserOffline() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        uid?.let {
            connectedUsersRepository.child(it).removeValue()
        }
    }
}