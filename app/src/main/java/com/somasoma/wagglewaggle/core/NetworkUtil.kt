package com.somasoma.wagglewaggle.core

import com.somasoma.wagglewaggle.core.model.dto.auth.RefreshRequest
import com.somasoma.wagglewaggle.core.usecase.PostRefreshUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkUtil @Inject constructor(
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
                    // TODO: 로그아웃 후 인트로 화면으로 이동
                    sharedPreferenceHelper.remove(PreferenceConstant.ACCESS_TOKEN)
                    sharedPreferenceHelper.remove(PreferenceConstant.ACCESS_TOKEN_EXPIRED_IN)
                    sharedPreferenceHelper.remove(PreferenceConstant.REFRESH_TOKEN)
                    sharedPreferenceHelper.remove(PreferenceConstant.REFRESH_TOKEN_EXPIRED_IN)
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
}