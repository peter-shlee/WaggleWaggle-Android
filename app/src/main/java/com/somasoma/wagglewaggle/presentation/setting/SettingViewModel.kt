package com.somasoma.wagglewaggle.presentation.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.PreferenceConstant
import com.somasoma.wagglewaggle.core.SharedPreferenceHelper
import com.somasoma.wagglewaggle.domain.usecase.DeleteAccountUseCase
import com.somasoma.wagglewaggle.domain.usecase.SignOutUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.DeleteLogoutUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.DeleteMemberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    application: Application,
    private val networkUtil: NetworkUtil,
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val deleteLogoutUseCase: DeleteLogoutUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val signOutUseCase: SignOutUseCase
) :
    AndroidViewModel(application) {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow

    fun onClickBackButton() {
        event(Event.NavigateToPrevPage)
    }

    fun onClickEditProfile() {
        event(Event.NavigateToEditProfile)
    }

    fun onClickSignOut() {
        networkUtil.restApiCall(deleteLogoutUseCase::deleteLogout, Unit, viewModelScope) {
            onSuccessCallback = {
                sharedPreferenceHelper.remove(PreferenceConstant.REFRESH_TOKEN)
                sharedPreferenceHelper.remove(PreferenceConstant.ACCESS_TOKEN)
                sharedPreferenceHelper.remove(PreferenceConstant.ACCESS_TOKEN_EXPIRED_IN)

                signOutUseCase.signOut({
                    event(Event.NavigateToSignInAndSignUp)
                }) {
                    event(Event.NavigateToSignInAndSignUp)
                }
            }

            onErrorCallback = {

            }

            onNetworkErrorCallback = {

            }
        }
    }

    fun onClickDeleteAccount() {
        networkUtil.restApiCall(deleteMemberUseCase::deleteMember, Unit, viewModelScope) {
            onSuccessCallback = {
                if (it?.status?.equals("ok") == true) {
                    sharedPreferenceHelper.remove(PreferenceConstant.REFRESH_TOKEN)
                    sharedPreferenceHelper.remove(PreferenceConstant.ACCESS_TOKEN)
                    sharedPreferenceHelper.remove(PreferenceConstant.ACCESS_TOKEN_EXPIRED_IN)
                    deleteAccountUseCase.deleteAccount({
                        event(Event.NavigateToSignInAndSignUp)
                    }, {})
                }
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

    sealed class Event{
        object NavigateToPrevPage: Event()
        object NavigateToEditProfile: Event()
        object NavigateToSignInAndSignUp: Event()
    }
}