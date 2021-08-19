package com.somasoma.speakworld.core.repository.firebase_repository

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.somasoma.speakworld.core.repository.AuthRepository
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor() : AuthRepository {

    override fun signOut(
        context: Context,
        onSuccessCallback: () -> Unit,
        onFailureCallback: () -> Unit
    ) {
        AuthUI.getInstance()
            .signOut(context)
            .addOnCompleteListener {
                onSuccessCallback()
            }.addOnCanceledListener {
                onFailureCallback()
            }
    }

    override fun deleteAccount(
        context: Context,
        onSuccessCallback: () -> Unit,
        onFailureCallback: () -> Unit
    ) {
        AuthUI.getInstance()
            .delete(context)
            .addOnCompleteListener { // 유저 계정 삭제 성공 시
                onSuccessCallback()
            }.addOnCanceledListener {
                onFailureCallback()
            }
    }
}