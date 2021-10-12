package com.somasoma.wagglewaggle.presentation.custom_views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectInterestsDialogViewModel @Inject constructor(
    application: Application
) :
    AndroidViewModel(application) {

    val closeDialogEvent = SingleLiveEvent<Unit>()
    private val interests = mutableSetOf<String>()
    private val selectedInterests = mutableSetOf<String>()

    fun setInterests(interests: Set<String>) {
        this.interests.clear()
        this.interests.addAll(interests)
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