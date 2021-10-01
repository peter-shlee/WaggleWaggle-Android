package com.somasoma.speakworld.domain.auth.sign_up

import android.app.Application
import com.somasoma.speakworld.core.SingleLiveEvent
import com.somasoma.speakworld.domain.custom_views.SelectInterestsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(application: Application): SelectInterestsViewModel(application) {

    val showSelectInterestsDialogEvent = SingleLiveEvent<Unit>()

    fun onClickSelectInterestButton() {
        showSelectInterestsDialogEvent.call()
    }
}