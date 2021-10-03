package com.somasoma.wagglewaggle.domain.auth.sign_up_set_name

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpSetNameViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val navigateToNextPageEvent = SingleLiveEvent<Unit>()
    private var name: String = ""

    fun getName(): String {
        return name
    }

    fun onNameChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        name = s.toString()
        Timber.d(name)
    }

    fun onClickNextButton() {
        if (name.isNotBlank()) {
            navigateToNextPageEvent.call()
        }
    }
}