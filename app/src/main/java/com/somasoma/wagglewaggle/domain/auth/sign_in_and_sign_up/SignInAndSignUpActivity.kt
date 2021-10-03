package com.somasoma.wagglewaggle.domain.auth.sign_in_and_sign_up

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.core.ApplicationService
import com.somasoma.wagglewaggle.core.usecase.UserConnectedStateUseCase
import com.somasoma.wagglewaggle.databinding.ActivitySignInAndSignUpBinding
import com.somasoma.wagglewaggle.domain.auth.sign_up_set_name.SignUpSetNameActivity
import com.somasoma.wagglewaggle.domain.home.HomeActivity
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

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            updateUI(user, false)
        }

        val intent = Intent(this, ApplicationService::class.java)
        startService(intent)
    }

    private fun observe() {
        viewModel.navigateToSignInPageEvent.observe(this) { navigateToFireBaseAuthSignIn() }
    }

    private fun updateUI(user: FirebaseUser?, isNewUser: Boolean) {
        user?.let {
            if (isNewUser) {
                navigateToSignUpSetNameActivity()
            } else {
                navigateToHomeActivity()
            }
        }
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

    private fun navigateToSignUpSetNameActivity() {
        val intent = Intent(this, SignUpSetNameActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToHomeActivity() {
        setCurrentUserOnline()

        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            Timber.d(user.toString())
            val isNewUser: Boolean = response?.isNewUser ?: false
            updateUI(user, isNewUser)
        } else {
            Timber.w("signInWithCredential:failure")
            // error dialog 띄우기
            updateUI(null, false)
        }
    }

    private fun setCurrentUserOnline() {
        userConnectedStateUseCase.registerOnUserConnectedStateCallback {}
        userConnectedStateUseCase.postCurrentUserOnline()
    }
}