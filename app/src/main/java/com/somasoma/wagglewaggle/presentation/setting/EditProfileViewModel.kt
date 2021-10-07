package com.somasoma.wagglewaggle.presentation.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val navigateToPrevPageEvent = SingleLiveEvent<Unit>()

    fun onClickBackButton() {
        navigateToPrevPageEvent.call()
    }
}