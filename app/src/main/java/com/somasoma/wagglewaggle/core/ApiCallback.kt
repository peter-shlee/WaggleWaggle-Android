package com.somasoma.wagglewaggle.core

class ApiCallback<T> {
    var onSuccessCallback: ((T?) -> Unit)? = null
    var onErrorCallback: ((Int) -> Unit)? = null
    var onNetworkErrorCallback: (() -> Unit)? = null
    var onFinishCallback: (() -> Unit)? = null

    fun onSuccess(body: T?) {
        onSuccessCallback?.invoke(body)
    }

    fun onError(code: Int) {
        onErrorCallback?.invoke(code)
    }

    fun onNetworkError() {
        onNetworkErrorCallback?.invoke()
    }

    fun onFinish() {
        onFinishCallback?.invoke()
    }
}