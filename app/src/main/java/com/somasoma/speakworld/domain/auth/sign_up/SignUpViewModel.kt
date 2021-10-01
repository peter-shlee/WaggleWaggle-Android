package com.somasoma.speakworld.domain.auth.sign_up

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.somasoma.speakworld.core.SingleLiveEvent
import com.somasoma.speakworld.domain.custom_views.SelectInterestsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(application: Application): SelectInterestsViewModel(application) {

    private val _nations = MutableLiveData<List<String>>()
    var nations: LiveData<List<String>> = _nations
    private val _languages = MutableLiveData<List<String>>()
    var languages: LiveData<List<String>> = _languages
    var selectedLanguage: String? = null
    var selectedNation: String? = null

    init {
        _nations.value = listOf("대한민국", "미국", "영국")
        _languages.value = listOf("한국어", "영어")
        resetSelectedInterests(setOf("스포츠", "BTS", "엔터"))
    }

    val showSelectInterestsDialogEvent = SingleLiveEvent<Unit>()

    fun onClickSelectInterestButton() {
        showSelectInterestsDialogEvent.call()
    }
}