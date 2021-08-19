package com.somasoma.speakworld.domain.auth.sign_up_set_language

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.speakworld.R
import com.somasoma.speakworld.databinding.ActivitySignUpSetLanguageBinding
import com.somasoma.speakworld.domain.home.HomeActivity
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
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}