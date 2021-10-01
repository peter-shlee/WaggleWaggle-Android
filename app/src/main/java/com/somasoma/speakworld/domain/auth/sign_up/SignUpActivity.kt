package com.somasoma.speakworld.domain.auth.sign_up

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.speakworld.R
import com.somasoma.speakworld.databinding.ActivitySignUpBinding
import com.somasoma.speakworld.domain.custom_views.SelectInterestsDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observe()
    }

    private fun observe() {
        viewModel.showSelectInterestsDialogEvent.observe(this) { showSelectInterestsDialog() }
    }

    private fun showSelectInterestsDialog() {
        val signUpSelectInterestsDialogFragment = SignUpSelectInterestsDialogFragment.newInstance()
        signUpSelectInterestsDialogFragment.show(supportFragmentManager, SelectInterestsDialogFragment::class.java.simpleName)
    }
}