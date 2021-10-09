package com.somasoma.wagglewaggle.presentation.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.PreferenceConstant
import com.somasoma.wagglewaggle.core.SharedPreferenceHelper
import com.somasoma.wagglewaggle.core.SingleLiveEvent
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
    private val signOutUseCase: SignOutUseCase
) :
    AndroidViewModel(application) {

    val navigateToPrevPageEvent = SingleLiveEvent<Unit>()
    val navigateToEditProfileEvent = SingleLiveEvent<Unit>()
    val navigateToSignInAndSignUpEvent = SingleLiveEvent<Unit>()
    private val compositeDisposable = CompositeDisposable()

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
}