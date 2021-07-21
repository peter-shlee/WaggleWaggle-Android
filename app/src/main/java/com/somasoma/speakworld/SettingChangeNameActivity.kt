package com.somasoma.speakworld

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.speakworld.databinding.ActivitySettingChangeNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingChangeNameActivity : AppCompatActivity() {

    private val viewModel: SettingChangeNameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySettingChangeNameBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting_change_name)
        binding.viewModel = viewModel
    }

    fun onClickSaveNameButton(view: View) {
        viewModel.saveNewUserName()
        finish()
    }
}