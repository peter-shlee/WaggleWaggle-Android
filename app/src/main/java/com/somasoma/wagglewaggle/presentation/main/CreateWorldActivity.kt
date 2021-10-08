package com.somasoma.wagglewaggle.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.databinding.ActivityCreateWorldBinding

class CreateWorldActivity : AppCompatActivity() {

    private val viewModel: CreateWorldViewModel by viewModels()
    private lateinit var binding: ActivityCreateWorldBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_world)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observe()
    }

    private fun observe() {
        viewModel.navigatePrevPageEvent.observe(this) { navigateToPrevPage() }
    }

    private fun navigateToPrevPage() {
        finish()
    }
}