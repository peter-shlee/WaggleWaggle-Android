package com.somasoma.wagglewaggle.presentation.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.PreferenceConstant
import com.somasoma.wagglewaggle.core.SharedPreferenceHelper
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import com.somasoma.wagglewaggle.domain.usecase.DeleteAccountUseCase
import com.somasoma.wagglewaggle.domain.usecase.SignOutUseCase
import com.somasoma.wagglewaggle.domain.usecase.member.DeleteLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    application: Application,
    private val networkUtil: NetworkUtil,
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val deleteLogoutUseCase: DeleteLogoutUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) :
    AndroidViewModel(application) {

    val navigateToPrevPageEvent = SingleLiveEvent<Unit>()
    val navigateToEditProfileEvent = SingleLiveEvent<Unit>()
    val navigateToSignInAndSignUpEvent = SingleLiveEvent<Unit>()
    private val compositeDisposable = CompositeDisposable()

    val navigateToSignInAndSignOutEvent = SingleLiveEvent<Unit>()
    val signOutFailedEvent = SingleLiveEvent<Unit>()
    val deleteAccountFailedEvent = SingleLiveEvent<Unit>()
    val removeUserInfoFailedEvent = SingleLiveEvent<Unit>()
    val navigateToChangeNameEvent = SingleLiveEvent<Unit>()
    val navigateToChangeLanguageEvent = SingleLiveEvent<Unit>()

    fun onClickBackButton() {
        navigateToPrevPageEvent.call()
    }

    fun onClickEditProfile() {
        navigateToEditProfileEvent.call()
    }

    fun onClickSignOut() {
        networkUtil.restApiCall(deleteLogoutUseCase.deleteLogout(), compositeDisposable) {
            onSuccessCallback = {
                sharedPreferenceHelper.remove(PreferenceConstant.REFRESH_TOKEN)
                sharedPreferenceHelper.remove(PreferenceConstant.ACCESS_TOKEN)
                sharedPreferenceHelper.remove(PreferenceConstant.ACCESS_TOKEN_EXPIRED_IN)

                signOutUseCase.signOut({
                    navigateToSignInAndSignUpEvent.call()
                }) {}
            }

            onErrorCallback = {

            }

            onNetworkErrorCallback = {

            }
        }
    }


    fun onClickChangeNameButton() {
        navigateToChangeNameEvent.call()
    }

    fun onClickChangeLanguageButton() {
        navigateToChangeLanguageEvent.call()
    }

    fun onClickSignOutButton() {
        signOutUseCase.signOut(
            onSuccessCallback = {
                navigateToSignInAndSignOutEvent.call()
            },
            onFailureCallback = { signOutFailedEvent.call() })
    }

    fun onClickDeleteAccountButton() {
        deleteAccountUseCase.deleteAccount(
            onSuccessCallback = {
                onDeleteAccountSuccess()
            },
            onFailureCallback = { deleteAccountFailedEvent.call() })
    }

    private fun onDeleteAccountSuccess() {
        navigateToSignInAndSignOutEvent.call()

        // TODO 보안 문제로 클라이언트에서는 유저가 탈퇴한 후 유저정보를 삭제할 수 없음 - 삭제된 유저의 uid 따로 저장 등으로 변경 필요함
//        deleteUserUseCase.deleteUser(
//            onSuccessCallback = {
//                navigateToSignInAndSignOutEvent.call()
//            },
//            onFailureCallback = {
//                removeUserInfoFailedEvent.call()
//                navigateToSignInAndSignOutEvent.call()
//            })
    }
}