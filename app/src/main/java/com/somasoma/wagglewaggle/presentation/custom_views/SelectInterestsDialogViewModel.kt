package com.somasoma.wagglewaggle.presentation.custom_views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectInterestsDialogViewModel @Inject constructor(
    application: Application
) :
    AndroidViewModel(application) {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow
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
        event(Event.CloseDialog)
    }

    fun onInterestKeywordClicked(interestKeyword: String, isSelected: Boolean) {
        if (isSelected) {
            selectedInterests.remove(interestKeyword)
        } else {
            selectedInterests.add(interestKeyword)
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event{
        object CloseDialog: Event()
    }
}