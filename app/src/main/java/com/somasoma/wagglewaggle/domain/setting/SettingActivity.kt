package com.somasoma.wagglewaggle.domain.setting

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.databinding.ActivitySettingBinding
import com.somasoma.wagglewaggle.domain.auth.sign_in_and_sign_up.SignInAndSignUpActivity
import com.somasoma.wagglewaggle.domain.setting.change_language.SettingChangeLanguageActivity
import com.somasoma.wagglewaggle.domain.setting.change_name.SettingChangeNameActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    private val viewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySettingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_setting)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observe()
    }

    private fun observe() {
        viewModel.navigateToPrevPageEvent.observe(this) { navigateToPrevPage() }

        viewModel.navigateToChangeNameEvent.observe(this) { navigateToChangeName() }
        viewModel.navigateToChangeLanguageEvent.observe(this) { navigateToChangeLanguage() }
        viewModel.navigateToSignInAndSignOutEvent.observe(this) { navigateToSignInAndSignOut() }
        viewModel.signOutFailedEvent.observe(this) { onSignOutFailed() }
        viewModel.deleteAccountFailedEvent.observe(this) { onDeleteAccountFailed() }
        viewModel.removeUserInfoFailedEvent.observe(this) { onRemoveUserInfoFailed() }
    }

    private fun navigateToPrevPage() {
        finish()
    }

    private fun navigateToChangeName() {
        val intent = Intent(this, SettingChangeNameActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToChangeLanguage() {
        val intent = Intent(this, SettingChangeLanguageActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSignInAndSignOut() {
        val intent = Intent(this, SignInAndSignUpActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun onSignOutFailed() {
        Toast.makeText(this, "sign out failed.", Toast.LENGTH_SHORT).show()
    }

    private fun onDeleteAccountFailed() {
        Toast.makeText(this, "failed to delete Account.", Toast.LENGTH_SHORT).show()
    }

    private fun onRemoveUserInfoFailed() {
        Toast.makeText(this, "failed to delete user info at DB.", Toast.LENGTH_SHORT).show()
    }
}