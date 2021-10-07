package com.somasoma.wagglewaggle.presentation.setting

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.databinding.ActivityEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private val viewModel: EditProfileViewModel by viewModels()
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observe()
    }

    private fun observe() {
        viewModel.navigateToPrevPageEvent.observe(this) { navigateToPrevPage() }
    }

    private fun navigateToPrevPage() {
        finish()
    }
}