package com.somasoma.speakworld.domain.setting.change_language

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.speakworld.R
import com.somasoma.speakworld.databinding.ActivitySettingChangeLanguageBinding

class SettingChangeLanguageActivity : AppCompatActivity() {

    private val viewModel: SettingChangeLanguageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding:ActivitySettingChangeLanguageBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_setting_change_language
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observe()
    }

    private fun observe() {
        viewModel.finishChangeLanguageEvent.observe(this) { finishChangeLanguage() }
    }

    private fun finishChangeLanguage() {
        finish()
    }
}