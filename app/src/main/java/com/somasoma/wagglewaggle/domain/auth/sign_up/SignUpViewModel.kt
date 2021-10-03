package com.somasoma.wagglewaggle.domain.auth.sign_up

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import com.somasoma.wagglewaggle.domain.custom_views.SelectInterestsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(application: Application): SelectInterestsViewModel(application) {
    companion object {
        private const val NICKNAME_REGEX = "[A-Za-z|가-힣|ㄱ-ㅎ|ㅏ-ㅣ\\d\\s]{2,8}\$"
        private val nicknamePattern = Pattern.compile(NICKNAME_REGEX)
    }

    private val _nations = MutableLiveData<List<String>>()
    var nations: LiveData<List<String>> = _nations
    private val _languages = MutableLiveData<List<String>>()
    var languages: LiveData<List<String>> = _languages
    var selectedLanguage: String? = null
    var selectedNation: String? = null
    private val _nicknameInputState = MutableLiveData(InputState.DISABLED)
    var nicknameInputState: LiveData<InputState> = _nicknameInputState
    private val _showDuplicateNicknameText = MutableLiveData(false)
    var showDuplicateNicknameText: LiveData<Boolean> = _showDuplicateNicknameText
    private var nickname: String = ""

    init {
        _nations.value = listOf("대한민국", "미국", "영국")
        _languages.value = listOf("한국어", "영어")
        resetSelectedInterests(setOf("스포츠", "BTS", "엔터"))
    }

    val showSelectInterestsDialogEvent = SingleLiveEvent<Unit>()

    fun onClickSelectInterestButton() {
        showSelectInterestsDialogEvent.call()
    }

    fun onClickCheckNicknameDuplicatedButton() {
        _nicknameInputState.value = InputState.POSITIVE
    }

    fun onNicknameChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _nicknameInputState.value = InputState.DISABLED
        _showDuplicateNicknameText.value = false
        nickname = s.toString()
        validateNickname(nickname)
    }

    private fun validateNickname(nickname: String) {
        if (nickname.isBlank()) {
            _nicknameInputState.value = InputState.DISABLED
            return
        }

        if (nicknamePattern.matcher(nickname).matches()) {
            _nicknameInputState.value = InputState.ENABLED
        } else {
            _nicknameInputState.value = InputState.NEGATIVE
        }
    }

    enum class InputState {
        DISABLED,
        ENABLED,
        NEGATIVE,
        POSITIVE
    }
}