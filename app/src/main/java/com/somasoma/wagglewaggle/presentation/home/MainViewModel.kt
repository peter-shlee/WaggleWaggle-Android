package com.somasoma.wagglewaggle.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import com.somasoma.wagglewaggle.domain.usecase.world.GetWorldListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val getWorldListUseCase: GetWorldListUseCase
) : AndroidViewModel(application) {
    var backgroundSemicircleRadius: Int = 0
    var backgroundSemicircleHeight: Int = 0

    val navigateToSettingEvent = SingleLiveEvent<Unit>()

    fun onClickSettingButton() {
        navigateToSettingEvent.call()
    }
}