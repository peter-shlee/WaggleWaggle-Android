package com.somasoma.speakworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject

class SignUpSetNameViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    var name: String = ""
}