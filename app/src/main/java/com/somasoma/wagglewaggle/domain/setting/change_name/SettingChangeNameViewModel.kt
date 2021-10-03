package com.somasoma.wagglewaggle.domain.setting.change_name

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import com.somasoma.wagglewaggle.core.usecase.SetUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingChangeNameViewModel @Inject constructor(
    application: Application,
    private val setUserNameUseCase: SetUserNameUseCase
) :
    AndroidViewModel(application) {

    val finishChangeNameEvent = SingleLiveEvent<Unit>()
    private var name: String = ""

    fun onNameChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        name = s.toString()
    }

    fun onClickSaveButton() {
        saveNewUserName { finishChangeNameEvent.call() }
    }

    private fun saveNewUserName(onSuccessCallback: () -> Unit) {
        if (name.isNotBlank()) {
            setUserNameUseCase.setUserName(name, onSuccessCallback)
        }
    }
}