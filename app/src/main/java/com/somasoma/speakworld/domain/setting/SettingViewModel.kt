package com.somasoma.speakworld.domain.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.speakworld.core.SingleLiveEvent
import com.somasoma.speakworld.core.usecase.DeleteAccountUseCase
import com.somasoma.speakworld.core.usecase.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    application: Application,
    private val signOutUseCase: SignOutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) :
    AndroidViewModel(application) {

    val navigateToSignInAndSignOutEvent = SingleLiveEvent<Unit>()
    val signOutFailedEvent = SingleLiveEvent<Unit>()
    val deleteAccountFailedEvent = SingleLiveEvent<Unit>()
    val removeUserInfoFailedEvent = SingleLiveEvent<Unit>()
    val navigateToChangeNameEvent = SingleLiveEvent<Unit>()
    val navigateToChangeLanguageEvent = SingleLiveEvent<Unit>()

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