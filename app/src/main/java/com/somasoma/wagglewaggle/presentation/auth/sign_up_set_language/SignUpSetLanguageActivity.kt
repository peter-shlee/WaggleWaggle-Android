package com.somasoma.wagglewaggle.presentation.auth.sign_up_set_language

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.databinding.ActivitySignUpSetLanguageBinding
import com.somasoma.wagglewaggle.presentation.auth.sign_in_and_sign_up.SignInAndSignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpSetLanguageActivity : AppCompatActivity() {
    companion object {
        private const val NAME = "name"
    }

    private val viewModel: SignUpSetLanguageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setName(intent.getStringExtra(NAME))

        val binding: ActivitySignUpSetLanguageBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_sign_up_set_language
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observe()
    }

    private fun observe() {
        viewModel.navigateToNextPageEvent.observe(this) { navigateToNextPage() }
    }

    private fun navigateToNextPage(){
        val intent = Intent(this, SignInAndSignUpActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
        startActivity(intent)
    }
}