package com.somasoma.wagglewaggle.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateWorldViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val navigatePrevPageEvent = SingleLiveEvent<Unit>()

    fun onBackButtonClicked() {
        navigatePrevPageEvent.call()
    }

}