package com.somasoma.speakworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SetNameViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    var firebaseUser: FirebaseUser? = null

    var name: String = ""
}