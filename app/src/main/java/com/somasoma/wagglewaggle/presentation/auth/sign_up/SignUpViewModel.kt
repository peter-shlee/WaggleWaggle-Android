package com.somasoma.wagglewaggle.presentation.auth.sign_up

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import com.somasoma.wagglewaggle.domain.usecase.member.GetCountryListUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetLanguageListUseCase
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    application: Application,
    private val networkUtil: NetworkUtil,
    private val getLanguageListUseCase: GetLanguageListUseCase,
    private val getCountryListUseCase: GetCountryListUseCase
) : SelectInterestsViewModel(application) {
    companion object {
        private const val NICKNAME_REGEX = "[A-Za-z|가-힣|ㄱ-ㅎ|ㅏ-ㅣ\\d\\s]{2,8}\$"
        private val nicknamePattern = Pattern.compile(NICKNAME_REGEX)
    }

    private val compositeDisposable = CompositeDisposable()
    private val _countries = MutableLiveData<List<String?>>()
    var countries: LiveData<List<String?>> = _countries
    private val _languages = MutableLiveData<List<String?>>()
    var languages: LiveData<List<String?>> = _languages
    var selectedLanguage: String? = null
    var selectedNation: String? = null
    private val _nicknameInputState = MutableLiveData(InputState.DISABLED)
    var nicknameInputState: LiveData<InputState> = _nicknameInputState
    private val _showDuplicateNicknameText = MutableLiveData(false)
    var showDuplicateNicknameText: LiveData<Boolean> = _showDuplicateNicknameText
    private var nickname: String = ""
    val showSelectInterestsDialogEvent = SingleLiveEvent<Unit>()

    init {
        getLanguageList()
        getCountryList()

        resetSelectedInterests(setOf("스포츠", "BTS", "엔터"))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun onClickSelectInterestButton() {
        showSelectInterestsDialogEvent.call()
    }

    fun onClickCheckNicknameDuplicatedButton() {
        _nicknameInputState.value = InputState.POSITIVE
    }

    fun onNicknameChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _nicknameInputState.value = InputState.DISABLED
        _showDuplicateNicknameText.value = false
        nickname = s.toString().trim()
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

    private fun getCountryList() {
        networkUtil.restApiCall(getCountryListUseCase.getCountryList(), compositeDisposable) {
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
        networkUtil.restApiCall(getLanguageListUseCase.getLanguageList(), compositeDisposable) {
            onSuccessCallback = {
                it?.languages?.let {
                    _languages.value = it
                }
            }

            onErrorCallback = {

            }
        }
    }

    enum class InputState {
        DISABLED,
        ENABLED,
        NEGATIVE,
        POSITIVE
    }
}