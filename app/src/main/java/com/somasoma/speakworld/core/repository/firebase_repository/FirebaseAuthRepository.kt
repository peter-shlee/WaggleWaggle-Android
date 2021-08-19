package com.somasoma.speakworld.core.repository.firebase_repository

import android.app.Application
import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.somasoma.speakworld.core.repository.AuthRepository
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(private val application: Application) : AuthRepository {

    override fun signOut(
        onSuccessCallback: () -> Unit,
        onFailureCallback: () -> Unit
    ) {
        AuthUI.getInstance()
            .signOut(application)
            .addOnCompleteListener {
                onSuccessCallback()
            }.addOnCanceledListener {
                onFailureCallback()
            }
    }

    override fun deleteAccount(
        onSuccessCallback: () -> Unit,
        onFailureCallback: () -> Unit
    ) {
        AuthUI.getInstance()
            .delete(application)
            .addOnCompleteListener { // 유저 계정 삭제 성공 시
                onSuccessCallback()
            }.addOnCanceledListener {
                onFailureCallback()
            }
    }
}