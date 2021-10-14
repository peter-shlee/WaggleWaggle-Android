package com.somasoma.wagglewaggle.presentation.auth.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.databinding.ActivitySplashBinding
import com.somasoma.wagglewaggle.presentation.auth.sign_in_and_sign_up.SignInAndSignUpActivity
import com.somasoma.wagglewaggle.presentation.base.BaseActivity
import com.somasoma.wagglewaggle.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this

        repeatOnStart { observe() }
    }

    override fun onStart() {
        super.onStart()

        FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.addOnCompleteListener {
            if (it.isSuccessful) {
                it.result?.token?.let { token ->
                    viewModel.firebaseUserToken = token
                    viewModel.getAccessToken()
                }
            } else {
                navigateToSignInAndSignUp()
            }
        } ?: navigateToSignInAndSignUp()
    }

    private suspend fun observe() {
        viewModel.eventFlow.collect { handleEvent(it) }
    }

    private fun handleEvent(event: SplashViewModel.Event) {
        when (event) {
            is SplashViewModel.Event.NavigateToMain -> {
                navigateToMain()
            }
            is SplashViewModel.Event.NavigateToSignInAndSignUp -> {
                navigateToSignInAndSignUp()
            }
        }
    }

    private fun navigateToSignInAndSignUp() {
        val navigateIntent = Intent(this, SignInAndSignUpActivity::class.java)
        navigateIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(navigateIntent)
    }

    private fun navigateToMain() {
        val navigateIntent = Intent(this, MainActivity::class.java)
        navigateIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(navigateIntent)
    }
}