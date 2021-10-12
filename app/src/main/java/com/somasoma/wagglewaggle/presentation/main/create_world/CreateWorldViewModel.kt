package com.somasoma.wagglewaggle.presentation.main.create_world

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import com.somasoma.wagglewaggle.data.WorldMap
import com.somasoma.wagglewaggle.domain.usecase.member.GetInterestListUseCase
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateWorldViewModel @Inject constructor(
    application: Application,
    private val networkUtil: NetworkUtil,
    getInterestListUseCase: GetInterestListUseCase
) :
    SelectInterestsViewModel(application, networkUtil, getInterestListUseCase) {

    val showSelectInterestsDialogEvent = SingleLiveEvent<Unit>()
    val navigatePrevPageEvent = SingleLiveEvent<Unit>()
    private val _maps = MutableLiveData<List<WorldMap>>()
    val maps: LiveData<List<WorldMap>> = _maps

    init {
        _maps.value = WorldMap.values().toList()
    }

    fun onClickBackButton() {
        navigatePrevPageEvent.call()
    }

    fun onClickSelectInterest() {
        showSelectInterestsDialogEvent.call()
    }

}