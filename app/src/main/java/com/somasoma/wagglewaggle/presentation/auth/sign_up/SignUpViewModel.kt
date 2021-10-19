package com.somasoma.wagglewaggle.presentation.auth.sign_up

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.somasoma.wagglewaggle.core.InputState
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.PreferenceConstant
import com.somasoma.wagglewaggle.core.SharedPreferenceHelper
import com.somasoma.wagglewaggle.data.model.dto.auth.SignUpRequest
import com.somasoma.wagglewaggle.domain.usecase.auth.PostSignUpUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetCountryListUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetInterestListUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetLanguageListUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetNicknameCheckUseCase
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    application: Application,
    private val networkUtil: NetworkUtil,
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val getLanguageListUseCase: GetLanguageListUseCase,
    private val getCountryListUseCase: GetCountryListUseCase,
    private val postSignUpUseCase: PostSignUpUseCase,
    private val getNicknameCheckUseCase: GetNicknameCheckUseCase,
    getInterestListUseCase: GetInterestListUseCase
) : SelectInterestsViewModel(application, networkUtil, getInterestListUseCase) {
    companion object {
        private const val NICKNAME_REGEX = "[A-Za-z|가-힣|ㄱ-ㅎ|ㅏ-ㅣ\\d\\s]{2,8}\$"
        private val nicknamePattern = Pattern.compile(NICKNAME_REGEX)
    }

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow
    private val _countries = MutableStateFlow<List<String?>>(listOf())
    var countries: StateFlow<List<String?>> = _countries
    private val _languages = MutableStateFlow<List<String?>>(listOf())
    var languages: StateFlow<List<String?>> = _languages
    private val _nicknameInputState = MutableStateFlow(InputState.DISABLED)
    var nicknameInputState: StateFlow<InputState> = _nicknameInputState
    private val _showDuplicateNicknameText = MutableStateFlow(false)
    var showDuplicateNicknameText: StateFlow<Boolean> = _showDuplicateNicknameText

    var selectedLanguage: String? = null
    var selectedCountry: String? = null
    private var nickname: String = ""
    private var firebaseAuthToken = ""

    init {
        getFirebaseAuthToken()
        getLanguageList()
        getCountryList()
    }

    fun onClickBackButton() {
        event(Event.NavigateToPrevPage)
    }

    fun onClickSelectInterestButton() {
        event(Event.ShowSelectInterestsDialog)
    }

    fun onClickCheckNicknameDuplicatedButton() {
        networkUtil.publicRestApiCall(
            getNicknameCheckUseCase::getNicknameCheck,
            nickname,
            viewModelScope
        ) {
            onSuccessCallback = {
                if (it?.isValid == true) {
                    _nicknameInputState.value = InputState.POSITIVE
                } else {
                    _nicknameInputState.value = InputState.NEGATIVE
                    _showDuplicateNicknameText.value = true
                }
            }

            onErrorCallback = {

            }

            onNetworkErrorCallback = {

            }
        }
    }

    fun onClickRegisterButton() {
        val selectedCountry = this.selectedCountry ?: return
        val selectedLanguage = this.selectedLanguage ?: return
        val selectedInterests = this.selectedInterests.value?.toList() ?: return

        networkUtil.publicRestApiCall(
            postSignUpUseCase::postSignUp, SignUpRequest(
                firebaseAuthToken,
                nickname,
                selectedCountry,
                selectedLanguage,
                "",
                selectedInterests
            ), viewModelScope
        ) {
            onSuccessCallback = {
                it?.run {
                    accessToken?.let {
                        sharedPreferenceHelper.putString(
                            PreferenceConstant.ACCESS_TOKEN,
                            accessToken
                        )
                    }

                    accessTokenExpiresIn?.let {
                        sharedPreferenceHelper.putLong(
                            PreferenceConstant.ACCESS_TOKEN_EXPIRED_IN,
                            accessTokenExpiresIn
                        )
                    }

                    refreshToken?.let {
                        sharedPreferenceHelper.putString(
                            PreferenceConstant.REFRESH_TOKEN,
                            refreshToken
                        )
                    }

                    memberId?.let {
                        sharedPreferenceHelper.putInt(
                            PreferenceConstant.MEMBER_ID,
                            memberId
                        )
                    }
                }

                event(Event.NavigateToMainPage)
            }

            onErrorCallback = {

            }

            onNetworkErrorCallback = {

            }
        }
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
        networkUtil.publicRestApiCall(getCountryListUseCase::getCountryList, Unit, viewModelScope) {
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
        networkUtil.publicRestApiCall(
            getLanguageListUseCase::getLanguageList,
            Unit,
            viewModelScope
        ) {
            onSuccessCallback = {
                it?.languages?.let {
                    _languages.value = it
                }
            }

            onErrorCallback = {

            }
        }
    }

    private fun getFirebaseAuthToken() {
        FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener {
            if (it.isSuccessful) {
                it.result?.token?.let { token ->
                    firebaseAuthToken = token
                }
            }
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        object NavigateToPrevPage : Event()
        object NavigateToMainPage : Event()
        object ShowSelectInterestsDialog : Event()
    }
}