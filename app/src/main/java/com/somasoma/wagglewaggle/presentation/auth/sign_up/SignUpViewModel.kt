package com.somasoma.wagglewaggle.presentation.auth.sign_up

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.somasoma.wagglewaggle.core.*
import com.somasoma.wagglewaggle.data.model.dto.auth.SignUpRequest
import com.somasoma.wagglewaggle.domain.usecase.auth.PostSignUpUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetCountryListUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetInterestListUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetLanguageListUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetNicknameCheckUseCase
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _countries = MutableLiveData<List<String?>>()
    var countries: LiveData<List<String?>> = _countries
    private val _languages = MutableLiveData<List<String?>>()
    var languages: LiveData<List<String?>> = _languages
    var selectedLanguage: String? = null
    var selectedCountry: String? = null
    private val _nicknameInputState = MutableLiveData(InputState.DISABLED)
    var nicknameInputState: LiveData<InputState> = _nicknameInputState
    private val _showDuplicateNicknameText = MutableLiveData(false)
    var showDuplicateNicknameText: LiveData<Boolean> = _showDuplicateNicknameText
    private var nickname: String = ""
    val showSelectInterestsDialogEvent = SingleLiveEvent<Unit>()
    val navigateToPrevPageEvent = SingleLiveEvent<Unit>()
    val navigateToMainEvent = SingleLiveEvent<Unit>()
    private var firebaseAuthToken = ""

    init {
        getFirebaseAuthToken()
        getLanguageList()
        getCountryList()
    }

    fun onClickBackButton() {
        navigateToPrevPageEvent.call()
    }

    fun onClickSelectInterestButton() {
        showSelectInterestsDialogEvent.call()
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
                        sharedPreferenceHelper.putLong(
                            PreferenceConstant.MEMBER_ID,
                            memberId
                        )
                    }
                }

                navigateToMainEvent.call()
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
}