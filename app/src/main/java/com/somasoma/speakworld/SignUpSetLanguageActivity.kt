package com.somasoma.speakworld

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.speakworld.databinding.ActivitySignUpSetLanguageBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignUpSetLanguageActivity : AppCompatActivity() {

    private val viewModel: SignUpSetLanguageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        viewModel.name = intent.getStringExtra("name")

        val binding: ActivitySignUpSetLanguageBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_set_language)
        binding.viewModel = viewModel

    }

    fun onClickLanguageButton(view: View){
        Timber.d(viewModel.language)
        if (viewModel.language == "") {
            return
        } else {
            viewModel.signUp()
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}