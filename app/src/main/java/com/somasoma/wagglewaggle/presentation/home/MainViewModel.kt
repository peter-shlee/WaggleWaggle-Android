package com.somasoma.wagglewaggle.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    var backgroundSemicircleRadius: Int = 0
    var backgroundSemicircleHeight: Int = 0

    val navigateToSettingEvent = SingleLiveEvent<Unit>()

    fun onClickSettingButton() {
        navigateToSettingEvent.call()
    }
}