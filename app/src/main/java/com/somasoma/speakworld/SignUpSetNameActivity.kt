package com.somasoma.speakworld

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.speakworld.databinding.ActivitySignUpSetNameBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignUpSetNameActivity : AppCompatActivity() {

    private val viewModel: SignUpSetNameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySignUpSetNameBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_set_name)
        binding.viewModel = viewModel

    }

    fun onClickNameButton(view: View){
        Timber.d(viewModel.name)
        if (viewModel.name == "") {
            return
        } else {
            val intent = Intent(this, SignUpSetLanguageActivity::class.java)
            intent.putExtra("name", viewModel.name)
            startActivity(intent)
        }
    }
}