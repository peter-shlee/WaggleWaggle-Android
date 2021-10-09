package com.somasoma.wagglewaggle.presentation.auth.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.PreferenceConstant
import com.somasoma.wagglewaggle.core.SharedPreferenceHelper
import com.somasoma.wagglewaggle.core.SingleLiveEvent
import com.somasoma.wagglewaggle.data.model.dto.auth.FirebaseRequest
import com.somasoma.wagglewaggle.domain.usecase.auth.PostFirebaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    application: Application,
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val networkUtil: NetworkUtil,
    private val postFirebaseUseCase: PostFirebaseUseCase
) : AndroidViewModel(application) {

    val navigateToSignInAndSignUpEvent = SingleLiveEvent<Unit>()
    val navigateToMainEvent = SingleLiveEvent<Unit>()
    var firebaseUserToken: String = ""
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


    fun getAccessToken() {
        networkUtil.publicRestApiCall(
            postFirebaseUseCase::postFirebase,
            FirebaseRequest(
                firebaseUserToken
            ),
            viewModelScope
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

                    isNewMember?.let {
                        if (isNewMember == "y") {
                            navigateToSignInAndSignUpEvent.call()
                        } else {
                            navigateToMainEvent.call()
                        }
                    }
                }


            }

            onErrorCallback = {

            }

            onNetworkErrorCallback = {

            }

            onFinishCallback = {

            }
        }
    }
}