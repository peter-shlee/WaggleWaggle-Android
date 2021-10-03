package com.somasoma.wagglewaggle.domain.setting.change_name

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.databinding.ActivitySettingChangeNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingChangeNameActivity : AppCompatActivity() {

    private val viewModel: SettingChangeNameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySettingChangeNameBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_setting_change_name
        )
        binding.viewModel = viewModel

        observe()
    }

    fun observe() {
        viewModel.finishChangeNameEvent.observe(this) { finishChangeName() }
    }

    private fun finishChangeName() {
        finish()
    }
}