package com.somasoma.speakworld.domain.custom_views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.somasoma.speakworld.core.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectInterestsDialogViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val closeDialogEvent = SingleLiveEvent<Unit>()
    private val _interests = MutableLiveData<Set<String>>()
    val interests: LiveData<Set<String>> = _interests
    private val selectedInterests = mutableSetOf<String>()

    init {
        _interests.value = setOf("스포츠", "BTS", "엔터", "웹툰", "자동차", "게임", "테크", "영화")
    }

    fun getSelectedInterests(): Set<String> {
        return selectedInterests
    }

    fun setSelectedInterests(selectedInterests: Set<String>) {
        this.selectedInterests.clear()
        this.selectedInterests.addAll(selectedInterests)
    }

    fun addSelectedInterests(interestsKeyword: String) {
        selectedInterests.add(interestsKeyword)
    }

    fun removeSelectedInterests(interestsKeyword: String) {
        selectedInterests.remove(interestsKeyword)
    }

    fun onClickOkayButton() {
        closeDialogEvent.call()
    }

    fun onInterestKeywordClicked(interestKeyword: String, isSelected: Boolean) {
        if (isSelected) {
            selectedInterests.remove(interestKeyword)
        } else {
            selectedInterests.add(interestKeyword)
        }
    }
}