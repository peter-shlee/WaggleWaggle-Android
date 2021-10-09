package com.somasoma.wagglewaggle.core

import android.app.Application
import android.content.Intent
import com.somasoma.wagglewaggle.data.model.dto.auth.RefreshRequest
import com.somasoma.wagglewaggle.domain.usecase.auth.PostRefreshUseCase
import com.somasoma.wagglewaggle.presentation.auth.sign_in_and_sign_up.SignInAndSignUpActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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
    fun <T> restApiCall(
        singleResponse: Single<Response<T>>,
        compositeDisposable: CompositeDisposable,
        setApiCallback: ApiCallback<T>.() -> Unit
    ) {
        val currentTime = System.currentTimeMillis()
        val authTokenExpiredIn =
            sharedPreferenceHelper.getLong(PreferenceConstant.ACCESS_TOKEN_EXPIRED_IN)

        if (currentTime >= authTokenExpiredIn) { // access token 만료, 토큰 재발급 후 apiCall 호출
            apiCall(
                postRefreshUseCase.postRefresh(
                    RefreshRequest(
                        sharedPreferenceHelper.getString(PreferenceConstant.ACCESS_TOKEN) ?: "",
                        sharedPreferenceHelper.getString(PreferenceConstant.REFRESH_TOKEN) ?: ""
                    )
                ),
                compositeDisposable
            ) {
                onSuccessCallback = {
                    it?.accessToken?.let { newAccessToken ->
                        sharedPreferenceHelper.putString(
                            PreferenceConstant.ACCESS_TOKEN,
                            newAccessToken
                        )
                        apiCall(singleResponse, compositeDisposable, setApiCallback)
                    }
                }

                onErrorCallback = { // refresh token 만료
                    signOut()
                }

                onNetworkErrorCallback = {

                }
            }
        } else {
            apiCall(singleResponse, compositeDisposable, setApiCallback)
        }
    }

    fun <T> publicRestApiCall(
        singleResponse: Single<Response<T>>,
        compositeDisposable: CompositeDisposable,
        setApiCallback: ApiCallback<T>.() -> Unit
    ) {
        apiCall(singleResponse, compositeDisposable, setApiCallback)
    }

    private fun <T> apiCall(
        singleResponse: Single<Response<T>>,
        compositeDisposable: CompositeDisposable,
        setApiCallback: ApiCallback<T>.() -> Unit
    ) {
        val apiCallback = ApiCallback<T>()
        setApiCallback(apiCallback)

        val disposable = singleResponse.run {
            subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                if (it.isSuccessful) {
                    apiCallback.onSuccess(it.body())
                    apiCallback.onFinish()
                } else {
                    apiCallback.onError(it.code())
                    apiCallback.onFinish()
                }
            }, {
                apiCallback.onNetworkError()
                apiCallback.onFinish()
            })
        }
        compositeDisposable.add(disposable)
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

        if (currentTime >= authTokenExpiredIn) { // access token 만료, 토큰 재발급 후 apiCall 호출
            scope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        postRefreshUseCase.postRefreshWithCoroutine(
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