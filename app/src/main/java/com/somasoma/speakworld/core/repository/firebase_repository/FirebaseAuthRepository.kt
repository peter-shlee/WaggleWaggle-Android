package com.somasoma.speakworld.core.repository.firebase_repository

import android.app.Application
import com.firebase.ui.auth.AuthUI
import com.somasoma.speakworld.core.repository.AuthRepository
import com.somasoma.speakworld.core.usecase.UserConnectedStateUseCase
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(private val application: Application) : AuthRepository {
    companion object {
        private const val FIREBASE_URL =
            "https://speak-world-default-rtdb.asia-southeast1.firebasedatabase.app"
    }

    @Inject
    lateinit var userConnectedStateUseCase: UserConnectedStateUseCase

    override fun signOut(
        onSuccessCallback: () -> Unit,
        onFailureCallback: () -> Unit
    ) {
        userConnectedStateUseCase.postCurrentUserOffline()

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
        userConnectedStateUseCase.postCurrentUserOffline()

        AuthUI.getInstance()
            .delete(application)
            .addOnCompleteListener { // 유저 계정 삭제 성공 시
                onSuccessCallback()
                signOut({}, {})
            }.addOnCanceledListener {
                onFailureCallback()
            }
    }
}