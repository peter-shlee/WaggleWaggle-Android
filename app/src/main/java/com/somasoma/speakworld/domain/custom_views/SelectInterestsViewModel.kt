package com.somasoma.speakworld.domain.custom_views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject

open class SelectInterestsViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private val selectedInterests = mutableSetOf<String>()

    fun resetSelectedInterests(newSelectedInterests: Set<String>) {
        selectedInterests.clear()
        selectedInterests.addAll(newSelectedInterests)
    }

    fun getSelectedInterests(): Set<String> {
        return selectedInterests
    }
}