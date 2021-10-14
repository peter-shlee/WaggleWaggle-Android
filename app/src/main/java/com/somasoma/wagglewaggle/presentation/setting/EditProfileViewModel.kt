package com.somasoma.wagglewaggle.presentation.setting

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.somasoma.wagglewaggle.core.InputState
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.PreferenceConstant
import com.somasoma.wagglewaggle.core.SharedPreferenceHelper
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import com.somasoma.wagglewaggle.domain.usecase.member.*
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    application: Application,
    private val networkUtil: NetworkUtil,
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val getMemberUseCase: GetMemberUseCase,
    private val getNicknameCheckUseCase: GetNicknameCheckUseCase,
    private val getLanguageListUseCase: GetLanguageListUseCase,
    private val getCountryListUseCase: GetCountryListUseCase,
    private val putEditMemberUseCase: PutEditMemberUseCase,
    getInterestListUseCase: GetInterestListUseCase
) : SelectInterestsViewModel(application, networkUtil, getInterestListUseCase) {
    companion object {
        private const val NICKNAME_REGEX = "[A-Za-z|가-힣|ㄱ-ㅎ|ㅏ-ㅣ\\d\\s]{2,8}\$"
        private val nicknamePattern = Pattern.compile(NICKNAME_REGEX)
    }

    private val _countries = MutableLiveData<List<String?>>(listOf())
    val countries: LiveData<List<String?>> = _countries

    private val _languages = MutableLiveData<List<String?>>(listOf())
    val languages: LiveData<List<String?>> = _languages

    private val _nicknameInputState = MutableStateFlow(InputState.DISABLED)
    val nicknameInputState: StateFlow<InputState> = _nicknameInputState

    private val _showDuplicateNicknameText = MutableStateFlow(false)
    val showDuplicateNicknameText: StateFlow<Boolean> = _showDuplicateNicknameText

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow

    private val _loadedNickname = MutableStateFlow("")
    val loadedNickname: StateFlow<String> = _loadedNickname

    private val _loadedLanguage = MutableLiveData<String>()
    val loadedLanguage: LiveData<String> = _loadedLanguage

    private val _loadedCountry = MutableLiveData<String>()
    val loadedCountry: LiveData<String> = _loadedCountry

    private val _loadedIntroduceMySelf = MutableStateFlow("")
    val loadedIntroduceMySelf: StateFlow<String> = _loadedIntroduceMySelf

    var selectedLanguage: String? = null
    var selectedCountry: String? = null
    var newNickname: String = ""
    var introduceMySelf: CharSequence = ""

    init {
        initViewModel()
    }

    private fun initViewModel() {
        getLanguageList()
        getCountryList()
        getMember()
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
            newNickname,
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

    fun onClickSaveButton() {
        val selectedCountry = this.selectedCountry ?: return
        val selectedLanguage = this.selectedLanguage ?: return
        val selectedInterests = this.selectedInterests.value?.toList() ?: return
        val newNickname = if (this.newNickname == loadedNickname.value) null else this.newNickname

        networkUtil.restApiCall(
            putEditMemberUseCase::putEditMember, Member(
                null,
                newNickname,
                selectedCountry,
                selectedLanguage,
                introduceMySelf.toString(),
                null,
                null,
                null,
                null,
                null,
                selectedInterests
            ), viewModelScope
        ) {
            onSuccessCallback = {
                event(Event.NavigateToPrevPage)
            }

            onErrorCallback = {

            }

            onNetworkErrorCallback = {

            }
        }
    }

    fun onIntroduceChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        introduceMySelf = s
    }

    fun onNicknameChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _nicknameInputState.value = InputState.DISABLED
        _showDuplicateNicknameText.value = false
        newNickname = s.toString().trim()
        validateNickname(newNickname)
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
                it?.countries?.let { countries ->
                    viewModelScope.launch(Dispatchers.Main) {
                        _countries.value = countries
                    }
                }
            }

            onErrorCallback = {

            }
        }
    }

    private fun getLanguageList() {
        networkUtil.restApiCall(getLanguageListUseCase::getLanguageList, Unit, viewModelScope) {
            onSuccessCallback = {
                it?.languages?.let { languages ->
                    viewModelScope.launch(Dispatchers.Main) {
                        _languages.value = languages
                    }
                }
            }

            onErrorCallback = {

            }
        }
    }

    private fun getMember() {
        networkUtil.restApiCall(
            getMemberUseCase::getMember,
            sharedPreferenceHelper.getLong(PreferenceConstant.MEMBER_ID),
            viewModelScope
        ) {
            onSuccessCallback = {
                _loadedNickname.value = it?.nickName ?: ""
                _loadedCountry.value = it?.country ?: ""
                _loadedLanguage.value = it?.language ?: ""
                _loadedIntroduceMySelf.value = it?.introduction ?: ""

                val tmpSelectedInterests = mutableSetOf<String>()
                it?.interests?.map { interest ->
                    if (!interest.isNullOrBlank()) tmpSelectedInterests.add(
                        interest
                    )
                }
                resetSelectedInterests(tmpSelectedInterests)
            }

            onErrorCallback = {

            }

            onNetworkErrorCallback = {

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
        object ShowSelectInterestsDialog : Event()
    }
}