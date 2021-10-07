package com.somasoma.wagglewaggle.presentation.follower_following

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.databinding.ActivityFollowerFollowingBinding
import com.somasoma.wagglewaggle.presentation.home.CreateWorldActivity
import com.somasoma.wagglewaggle.presentation.home.MainActivity
import com.somasoma.wagglewaggle.presentation.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerFollowingActivity : AppCompatActivity() {

    private val viewModel: FollowerFollowingViewModel by viewModels()
    private lateinit var binding: ActivityFollowerFollowingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_follower_following)
        binding.viewModel = viewModel
        observe()
    }

    private fun observe() {
        viewModel.navigateToCreateWorldEvent.observe(this) { navigateToCreateWorld() }
        viewModel.navigateToMainEvent.observe(this) { navigateToMain() }
        viewModel.navigateToSettingEvent.observe(this) { navigateToSettingEvent() }
    }

    private fun navigateToCreateWorld() {
        val navigateIntent = Intent(this, CreateWorldActivity::class.java)
        startActivity(navigateIntent)
    }

    private fun navigateToMain() {
        val navigateIntent = Intent(this, MainActivity::class.java)
        navigateIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivity(navigateIntent)
    }

    private fun navigateToSettingEvent() {
        val navigateIntent = Intent(this, SettingActivity::class.java)
        startActivity(navigateIntent)
    }
}