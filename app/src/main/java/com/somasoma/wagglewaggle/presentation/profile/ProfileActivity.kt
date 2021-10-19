package com.somasoma.wagglewaggle.presentation.profile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.databinding.ActivityProfileBinding
import com.somasoma.wagglewaggle.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber

@AndroidEntryPoint
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
        binding.listInterest.adapter
        setLayoutManager()

        collect()
    }

    private fun setLayoutManager() {
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        binding.listInterest.layoutManager = layoutManager
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