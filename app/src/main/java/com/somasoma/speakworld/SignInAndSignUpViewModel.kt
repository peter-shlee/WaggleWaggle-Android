package com.somasoma.speakworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInAndSignUpViewModel @Inject constructor(application: Application): AndroidViewModel(application) {
}