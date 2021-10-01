package com.somasoma.speakworld.domain.custom_views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

open class SelectInterestsViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private val _selectedInterests = MutableLiveData<MutableSet<String>>()
    var selectedInterests: LiveData<MutableSet<String>> = _selectedInterests

    fun resetSelectedInterests(newSelectedInterests: Set<String>) {
        _selectedInterests.value = newSelectedInterests.toMutableSet()
    }

    fun getSelectedInterests(): Set<String>? {
        return selectedInterests.value
    }
}