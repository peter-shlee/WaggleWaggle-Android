package com.somasoma.speakworld.domain.auth.sign_in_and_sign_up

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.speakworld.core.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInAndSignUpViewModel @Inject constructor(application: Application): AndroidViewModel(application) {
    internal val navigateToSignInPageEvent = SingleLiveEvent<Unit>()

    fun onClickSignInButton() {
        navigateToSignInPageEvent.call()
    }
}