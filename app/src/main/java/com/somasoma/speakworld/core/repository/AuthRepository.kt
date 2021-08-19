package com.somasoma.speakworld.core.repository

import android.content.Context

interface AuthRepository {
    fun signOut(context: Context, onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit)
    fun deleteAccount(
        context: Context,
        onSuccessCallback: () -> Unit,
        onFailureCallback: () -> Unit
    )
}