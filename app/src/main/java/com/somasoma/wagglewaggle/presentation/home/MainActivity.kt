package com.somasoma.wagglewaggle.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.core.dp2Px
import com.somasoma.wagglewaggle.data.Avatar
import com.somasoma.wagglewaggle.databinding.ActivityMainBinding
import com.somasoma.wagglewaggle.presentation.follower_following.FollowerFollowingActivity
import com.somasoma.wagglewaggle.presentation.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private const val BACKGROUND_SEMICIRCLE_HEIGHT_IN_DP = 295
        private const val BACKGROUND_SEMICIRCLE_HORIZONTAL_MARGIN_IN_DP = 24
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var avatarSelectViewPagerAdapter = AvatarSelectPagerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.viewpagerSelectCharacter.adapter = avatarSelectViewPagerAdapter
        binding.viewpagerSelectCharacter.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.onAvatarSelected(position)
            }
        })

        observe()
    }

    fun observe() {
        viewModel.navigateToSettingEvent.observe(this) { navigateToSettingActivity() }
        viewModel.navigateToCreateWorld.observe(this) { navigateToCreateWorldActivity() }
        viewModel.navigateToFollowerFollowing.observe(this) { navigateToFollowerFollowingActivity() }
        viewModel.avatars.observe(this) { onAvatarListLoaded(it) }
        viewModel.scrollToNextAvatarEvent.observe(this) { scrollToNextAvatar() }
        viewModel.scrollToPrevAvatarEvent.observe(this) { scrollToPrevAvatar() }
    }

    private fun onAvatarListLoaded(avatarList: List<Avatar>) {
        avatarSelectViewPagerAdapter.submitList(avatarList)
    }

    private fun scrollToNextAvatar() {
        binding.viewpagerSelectCharacter.currentItem =
            binding.viewpagerSelectCharacter.currentItem + 1
    }

    private fun scrollToPrevAvatar() {
        binding.viewpagerSelectCharacter.currentItem =
            binding.viewpagerSelectCharacter.currentItem - 1
    }

    private fun navigateToSettingActivity() {
        val navigateIntent = Intent(this, SettingActivity::class.java)
        startActivity(navigateIntent)
    }

    private fun navigateToFollowerFollowingActivity() {
        val navigateIntent = Intent(this, FollowerFollowingActivity::class.java)
        navigateIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivity(navigateIntent)
    }

    private fun navigateToCreateWorldActivity() {
        val navigateIntent = Intent(this, CreateWorldActivity::class.java)
        startActivity(navigateIntent)
    }

    private fun initViewModel() {
        val screenWidth = getScreenWidth()
        viewModel.backgroundSemicircleHeight = calculateBackgroundSemicircleHeight(screenWidth)
        viewModel.backgroundSemicircleRadius = calculateBackgroundSemicircleRadius(screenWidth)
    }

    private fun getScreenWidth() = if (android.os.Build.VERSION.SDK_INT < 30) {
        windowManager.defaultDisplay.width
    } else {
        val metrics = windowManager.currentWindowMetrics
        val windowInsets = metrics.windowInsets
        val insets =
            windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout())
        val insetsWidth = insets.left + insets.right
        val bounds = metrics.bounds
        val width = bounds.width() - insetsWidth
        width
    }

    private fun calculateBackgroundSemicircleRadius(screenWidth: Int) = (screenWidth / 2) + dp2Px(
        this,
        BACKGROUND_SEMICIRCLE_HORIZONTAL_MARGIN_IN_DP.toFloat()
    )

    private fun calculateBackgroundSemicircleHeight(screenWidth: Int) =
        dp2Px(this, BACKGROUND_SEMICIRCLE_HEIGHT_IN_DP.toFloat())
}