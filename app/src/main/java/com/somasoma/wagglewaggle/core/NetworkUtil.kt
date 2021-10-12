package com.somasoma.wagglewaggle.core

import android.app.Application
import android.content.Intent
import com.somasoma.wagglewaggle.data.model.dto.auth.RefreshRequest
import com.somasoma.wagglewaggle.domain.usecase.auth.PostRefreshUseCase
import com.somasoma.wagglewaggle.presentation.auth.sign_in_and_sign_up.SignInAndSignUpActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkUtil @Inject constructor(
    private val application: Application,
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val postRefreshUseCase: PostRefreshUseCase
) {
    companion object {
        private const val MINUTE_IN_SECONDS = 60
        private const val MINUTES_BEFORE_TOKEN_EXPIRES = 30
    }

    fun <T, S> restApiCall(
        useCase: suspend (T) -> Response<S>,
        request: T,
        scope: CoroutineScope,
        setApiCallback: ApiCallback<S>.() -> Unit
    ) {
        val currentTime = System.currentTimeMillis()
        val authTokenExpiredIn =
            sharedPreferenceHelper.getLong(PreferenceConstant.ACCESS_TOKEN_EXPIRED_IN)

        if (currentTime >= authTokenExpiredIn - (MINUTES_BEFORE_TOKEN_EXPIRES * MINUTE_IN_SECONDS)) { // 토큰 재발급 후 apiCall 호출
            scope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        postRefreshUseCase.postRefresh(
                            RefreshRequest(
                                sharedPreferenceHelper.getString(PreferenceConstant.ACCESS_TOKEN)
                                    ?: "",
                                sharedPreferenceHelper.getString(PreferenceConstant.REFRESH_TOKEN)
                                    ?: ""
                            )
                        )
                    }

                    if (response.isSuccessful) {
                        response.body()?.accessToken?.let { newToken ->
                            sharedPreferenceHelper.putString(
                                PreferenceConstant.ACCESS_TOKEN,
                                newToken
                            )

                        }

                        response.body()?.accessTokenExpiresIn?.let { newTokenExpiredIn ->
                            sharedPreferenceHelper.putLong(
                                PreferenceConstant.ACCESS_TOKEN_EXPIRED_IN,
                                newTokenExpiredIn
                            )
                        }

                        apiCall(useCase, request, scope, setApiCallback)
                    } else { // refresh token 만료
                        signOut()
                    }
                } catch (exception: Exception) {
                    Timber.e(exception.message.toString())
                }
            }
        } else {
            apiCall(useCase, request, scope, setApiCallback)
        }
    }

    fun <T, S> publicRestApiCall(
        useCase: suspend (T) -> Response<S>,
        request: T,
        scope: CoroutineScope,
        setApiCallback: ApiCallback<S>.() -> Unit
    ) {
        apiCall(useCase, request, scope, setApiCallback)
    }

    private fun <T, S> apiCall(
        useCase: suspend (T) -> Response<S>,
        request: T,
        scope: CoroutineScope,
        setApiCallback: ApiCallback<S>.() -> Unit
    ) {
        val apiCallback = ApiCallback<S>()
        setApiCallback(apiCallback)

        scope.launch {
            try {
                val response = withContext(Dispatchers.IO) { useCase(request) }

                if (response.isSuccessful) {
                    apiCallback.onSuccess(response.body())
                } else {
                    apiCallback.onError(response.code())
                }
            } catch (exception: Exception) {
                Timber.e(exception.toString())
                apiCallback.onNetworkError()
            } finally {
                apiCallback.onFinish()
            }
        }
    }

    private fun signOut() {
        removeTokens()
        navigateToSignInAndSignUp()
    }

    private fun removeTokens() {
        sharedPreferenceHelper.remove(PreferenceConstant.ACCESS_TOKEN)
        sharedPreferenceHelper.remove(PreferenceConstant.ACCESS_TOKEN_EXPIRED_IN)
        sharedPreferenceHelper.remove(PreferenceConstant.REFRESH_TOKEN)
        sharedPreferenceHelper.remove(PreferenceConstant.REFRESH_TOKEN_EXPIRED_IN)
    }

    private fun navigateToSignInAndSignUp() {
        val navigateIntent = Intent(application, SignInAndSignUpActivity::class.java)
        navigateIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        application.startActivity(navigateIntent)
    }
}