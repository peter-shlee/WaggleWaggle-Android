package com.somasoma.speakworld.domain.auth.sign_up_set_name

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.speakworld.R
import com.somasoma.speakworld.databinding.ActivitySignUpSetNameBinding
import com.somasoma.speakworld.domain.auth.sign_up_set_language.SignUpSetLanguageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpSetNameActivity : AppCompatActivity() {
    companion object {
        private const val NAME = "name"
    }

    private val viewModel: SignUpSetNameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySignUpSetNameBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_sign_up_set_name
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observe()
    }

    private fun observe() {
        viewModel.navigateToNextPageEvent.observe(this) { navigateToNextPage() }
    }

    private fun navigateToNextPage() {
        val intent = Intent(this, SignUpSetLanguageActivity::class.java)
        intent.putExtra(NAME, viewModel.getName())
        startActivity(intent)
    }
}