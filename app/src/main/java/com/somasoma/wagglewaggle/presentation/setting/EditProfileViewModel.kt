package com.somasoma.wagglewaggle.presentation.setting

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import com.somasoma.wagglewaggle.domain.usecase.member.GetCountryListUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetInterestListUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetLanguageListUseCase
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    application: Application,
    private val networkUtil: NetworkUtil,
    private val getLanguageListUseCase: GetLanguageListUseCase,
    private val getCountryListUseCase: GetCountryListUseCase,
    getInterestListUseCase: GetInterestListUseCase
) :
    SelectInterestsViewModel(application, networkUtil, getInterestListUseCase) {

    val showSelectInterestsDialogEvent = SingleLiveEvent<Unit>()
    val navigateToPrevPageEvent = SingleLiveEvent<Unit>()
    private val _countries = MutableLiveData<List<String?>>()
    val countries: LiveData<List<String?>> = _countries
    private val _languages = MutableLiveData<List<String?>>()
    val languages: LiveData<List<String?>> = _languages
    var selectedLanguage: String? = null
    var selectedCountry: String? = null

    init {
        getLanguageList()
        getCountryList()

        resetSelectedInterests(setOf("스포츠", "BTS"))
    }

    fun onClickBackButton() {
        navigateToPrevPageEvent.call()
    }

    fun onClickSelectInterestButton() {
        showSelectInterestsDialogEvent.call()
    }

    private fun getCountryList() {
        networkUtil.restApiCall(getCountryListUseCase::getCountryList, Unit, viewModelScope) {
            onSuccessCallback = {
                it?.countries?.let {
                    _countries.value = it
                }
            }

            onErrorCallback = {

            }
        }
    }

    private fun getLanguageList() {
        networkUtil.restApiCall(getLanguageListUseCase::getLanguageList, Unit, viewModelScope) {
            onSuccessCallback = {
                it?.languages?.let {
                    _languages.value = it
                }
            }

            onErrorCallback = {

            }
        }
    }
}