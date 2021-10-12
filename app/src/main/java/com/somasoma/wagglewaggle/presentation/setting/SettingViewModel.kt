package com.somasoma.wagglewaggle.presentation.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.PreferenceConstant
import com.somasoma.wagglewaggle.core.SharedPreferenceHelper
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import com.somasoma.wagglewaggle.domain.usecase.DeleteAccountUseCase
import com.somasoma.wagglewaggle.domain.usecase.SignOutUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.DeleteLogoutUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.DeleteMemberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val navigateToPrevPageEvent = SingleLiveEvent<Unit>()
    val navigateToEditProfileEvent = SingleLiveEvent<Unit>()
    val navigateToSignInAndSignUpEvent = SingleLiveEvent<Unit>()

    fun onClickBackButton() {
        navigateToPrevPageEvent.call()
    }

    fun onClickEditProfile() {
        navigateToEditProfileEvent.call()
    }

    fun onClickSignOut() {
        networkUtil.restApiCall(deleteLogoutUseCase::deleteLogout, Unit, viewModelScope) {
            onSuccessCallback = {
                sharedPreferenceHelper.remove(PreferenceConstant.REFRESH_TOKEN)
                sharedPreferenceHelper.remove(PreferenceConstant.ACCESS_TOKEN)
                sharedPreferenceHelper.remove(PreferenceConstant.ACCESS_TOKEN_EXPIRED_IN)

                signOutUseCase.signOut({
                    navigateToSignInAndSignUpEvent.call()
                }) {
                    navigateToSignInAndSignUpEvent.call()
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
                        navigateToSignInAndSignUpEvent.call()
                    }, {})
                }
            }

            onErrorCallback = {

            }

            onNetworkErrorCallback = {

            }
        }
    }
}