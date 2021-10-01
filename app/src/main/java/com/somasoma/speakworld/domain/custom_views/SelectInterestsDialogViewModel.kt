package com.somasoma.speakworld.domain.custom_views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.speakworld.core.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectInterestsDialogViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val closeDialogEvent = SingleLiveEvent<Unit>()

    fun onClickOkayButton() {
        closeDialogEvent.call()
    }
}