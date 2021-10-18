package com.somasoma.wagglewaggle.presentation.profile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.databinding.ActivityProfileBinding
import com.somasoma.wagglewaggle.presentation.base.BaseActivity
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber

class ProfileActivity : BaseActivity() {
    companion object {
        private const val MEMBER = "member"
    }

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val stringMember = intent.getStringExtra(MEMBER)
        if (!stringMember.isNullOrBlank()) {
            viewModel.member = Json.decodeFromString(stringMember)
            Timber.d(viewModel.member.toString())
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        collect()
    }

    private fun collect() {
        repeatOnStart { viewModel.eventFlow.collect { handleEvent(it) } }
    }

    private fun handleEvent(event: ProfileViewModel.Event) = when (event) {
        ProfileViewModel.Event.NavigateToPrevPage -> navigateToPrevPage()
    }

    private fun navigateToPrevPage() {
        finish()
    }
}