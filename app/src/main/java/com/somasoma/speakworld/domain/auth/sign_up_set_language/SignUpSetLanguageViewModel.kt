package com.somasoma.speakworld.domain.auth.sign_up_set_language

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.speakworld.core.SingleLiveEvent
import com.somasoma.speakworld.core.model.User
import com.somasoma.speakworld.core.usecase.WriteNewUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpSetLanguageViewModel @Inject constructor(
    application: Application,
    private val writeNewUserUserCase: WriteNewUserUseCase
) :
    AndroidViewModel(application) {

    val navigateToNextPageEvent = SingleLiveEvent<Unit>()
    private var name: String? = null
    private var language: String = ""

    fun setName(name: String?) {
        this.name = name
    }

    fun onClickNextButton() {
        if (language.isNotBlank()) {
            signUp { navigateToNextPageEvent.call() }
        }
    }

    fun onLanguageChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        language = s.toString()
    }

    private fun signUp(onSuccessCallback: () -> Unit) {
        name?.let { name ->
            writeNewUserUserCase.writeNewUser(
                User(name = name, language = language),
                onSuccessCallback
            )
        }
    }
}