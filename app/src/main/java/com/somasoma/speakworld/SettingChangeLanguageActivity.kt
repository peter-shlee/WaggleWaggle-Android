package com.somasoma.speakworld

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.speakworld.databinding.ActivitySettingChangeLanguageBinding

class SettingChangeLanguageActivity : AppCompatActivity() {

    private val viewModel: SettingChangeLanguageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding:ActivitySettingChangeLanguageBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting_change_language)
        binding.viewModel = viewModel
    }

    fun onClickSaveLanguageButton(view: View) {
        viewModel.saveNewUserLanguage()
        finish()
    }
}