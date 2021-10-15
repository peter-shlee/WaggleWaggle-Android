package com.somasoma.wagglewaggle.presentation.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.databinding.ActivitySettingBinding
import com.somasoma.wagglewaggle.presentation.auth.sign_in_and_sign_up.SignInAndSignUpActivity
import com.somasoma.wagglewaggle.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SettingActivity : BaseActivity() {

    private val viewModel: SettingViewModel by viewModels()
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observe()
    }

    private fun observe() {
        repeatOnStart { viewModel.eventFlow.collect { handleEvent(it) } }
    }

    private fun handleEvent(event: SettingViewModel.Event) = when (event) {
        SettingViewModel.Event.NavigateToSignInAndSignUp -> navigateToSignInAndSignUp()
        SettingViewModel.Event.NavigateToEditProfile -> navigateToEditProfile()
        SettingViewModel.Event.NavigateToPrevPage -> navigateToPrevPage()
    }

    private fun navigateToPrevPage() {
        finish()
    }

    private fun navigateToEditProfile() {
        val navigateIntent = Intent(this, EditProfileActivity::class.java)
        startActivity(navigateIntent)
    }

    private fun navigateToSignInAndSignUp() {
        val navigateIntent = Intent(this, SignInAndSignUpActivity::class.java)
        navigateIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(navigateIntent)
    }
}