package com.somasoma.wagglewaggle.presentation.setting

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.somasoma.wagglewaggle.core.InputState
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import com.somasoma.wagglewaggle.domain.usecase.member.GetCountryListUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetInterestListUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.GetLanguageListUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.PutEditMemberUseCase
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    application: Application,
    private val networkUtil: NetworkUtil,
    private val getLanguageListUseCase: GetLanguageListUseCase,
    private val getCountryListUseCase: GetCountryListUseCase,
    private val putEditMemberUseCase: PutEditMemberUseCase,
    getInterestListUseCase: GetInterestListUseCase
) : SelectInterestsViewModel(application, networkUtil, getInterestListUseCase) {
    companion object {
        private const val NICKNAME_REGEX = "[A-Za-z|가-힣|ㄱ-ㅎ|ㅏ-ㅣ\\d\\s]{2,8}\$"
        private val nicknamePattern = Pattern.compile(NICKNAME_REGEX)
    }

    val showSelectInterestsDialogEvent = SingleLiveEvent<Unit>()
    val navigateToPrevPageEvent = SingleLiveEvent<Unit>()
    private val _countries = MutableLiveData<List<String?>>()
    val countries: LiveData<List<String?>> = _countries
    private val _languages = MutableLiveData<List<String?>>()
    val languages: LiveData<List<String?>> = _languages
    var selectedLanguage: String? = null
    var selectedCountry: String? = null
    private val _nicknameInputState = MutableLiveData(InputState.DISABLED)
    val nicknameInputState: LiveData<InputState> = _nicknameInputState
    private val _showDuplicateNicknameText = MutableLiveData(false)
    val showDuplicateNicknameText: LiveData<Boolean> = _showDuplicateNicknameText
    private var nickname: String = ""
    var introduceMySelf: CharSequence = ""

    init {
        getLanguageList()
        getCountryList()
        resetSelectedInterests(setOf())
    }

    fun onClickBackButton() {
        navigateToPrevPageEvent.call()
    }

    fun onClickSelectInterestButton() {
        showSelectInterestsDialogEvent.call()
    }

    fun onClickCheckNicknameDuplicatedButton() {
        _nicknameInputState.value = InputState.POSITIVE
    }

    fun onClickSaveButton() {
        networkUtil.restApiCall(
            putEditMemberUseCase::putEditMember, Member(
                null,
                nickname,
                selectedCountry,
                selectedLanguage,
                introduceMySelf.toString(),
                null,
                null,
                null,
                null,
                null
            ), viewModelScope
        ) {

        }
    }

    fun onIntroduceChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        introduceMySelf = s
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
        networkUtil.restApiCall(getCountryListUseCase::getCountryList, Unit, viewModelScope) {
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
        networkUtil.restApiCall(getLanguageListUseCase::getLanguageList, Unit, viewModelScope) {
            onSuccessCallback = {
                it?.languages?.let {
                    _languages.value = it
                }
            }

            onErrorCallback = {

            }
        }
    }
}