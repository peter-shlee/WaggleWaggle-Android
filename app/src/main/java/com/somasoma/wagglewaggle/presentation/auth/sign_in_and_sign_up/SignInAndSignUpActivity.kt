package com.somasoma.wagglewaggle.presentation.auth.sign_in_and_sign_up

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.domain.usecase.UserConnectedStateUseCase
import com.somasoma.wagglewaggle.databinding.ActivitySignInAndSignUpBinding
import com.somasoma.wagglewaggle.presentation.auth.sign_up.SignUpActivity
import com.somasoma.wagglewaggle.presentation.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SignInAndSignUpActivity : AppCompatActivity() {

    @Inject
    lateinit var userConnectedStateUseCase: UserConnectedStateUseCase

    private val viewModel: SignInAndSignUpViewModel by viewModels()

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySignInAndSignUpBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_sign_in_and_sign_up)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observe()
    }

    override fun onStart() {
        super.onStart()

        FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener(
            OnCompleteListener {
                if (it.isSuccessful) {
                    it.result?.token?.let { token ->
                        viewModel.firebaseUserToken = token
                            viewModel.getAccessToken()
                    }
                }
            })

        // TODO 유저 접속상태 체크를 위해 사용했던 서비스인데, 이제 접속상태 체크 방식이 바뀌어 삭제해야 함.
//        val intent = Intent(this, ApplicationService::class.java)
//        startService(intent)
    }

    private fun observe() {
        viewModel.navigateToSignInPageEvent.observe(this) { navigateToFireBaseAuthSignIn() }
        viewModel.navigateToMainEvent.observe(this) { navigateToMainActivity() }
        viewModel.navigateToSignUpEvent.observe(this) { navigateToSignUpActivity() }
    }

    private fun navigateToFireBaseAuthSignIn() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun navigateToMainActivity() {
        val navigateIntent = Intent(this, MainActivity::class.java)
        navigateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        navigateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        navigateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(navigateIntent)
    }

    private fun navigateToSignUpActivity() {
        val navigateIntent = Intent(this, SignUpActivity::class.java)
        startActivity(navigateIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            Timber.d(FirebaseAuth.getInstance().currentUser.toString())
            FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener(
                OnCompleteListener {
                    if (it.isSuccessful) {
                        it.result?.token?.let { token ->
                            viewModel.firebaseUserToken = token
                            viewModel.getAccessToken()
                        }
                    }
                })
        } else {
            Timber.w("signInWithCredential:failure")
            // TODO: error dialog 띄우기
        }
    }

    private fun setCurrentUserOnline() {
        // TODO 유저 접속상태 체크를 위해 사용했던 서비스인데, 이제 접속상태 체크 방식이 바뀌어 삭제해야 함.
        userConnectedStateUseCase.registerOnUserConnectedStateCallback {}
        userConnectedStateUseCase.postCurrentUserOnline()
    }
}