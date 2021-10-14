package com.somasoma.wagglewaggle.presentation.auth.sign_in_and_sign_up

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.somasoma.wagglewaggle.core.NetworkUtil
import com.somasoma.wagglewaggle.core.PreferenceConstant
import com.somasoma.wagglewaggle.core.SharedPreferenceHelper
import com.somasoma.wagglewaggle.data.model.dto.auth.FirebaseRequest
import com.somasoma.wagglewaggle.domain.usecase.auth.PostFirebaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInAndSignUpViewModel @Inject constructor(
    application: Application,
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val networkUtil: NetworkUtil,
    private val postFirebaseUseCase: PostFirebaseUseCase
) : AndroidViewModel(application) {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow
    var firebaseUserToken = ""

    fun onClickSignInButton() {
        event(Event.NavigateToFirebaseSignIn)
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
                    isNewMember?.let {
                        if (isNewMember == "y") {
                            event(Event.NavigateToSignUp)
                        } else {
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

                            event(Event.NavigateToMain)
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

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event{
        object NavigateToFirebaseSignIn : Event()
        object NavigateToMain : Event()
        object NavigateToSignUp : Event()
    }
}