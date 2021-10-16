package com.somasoma.wagglewaggle.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.core.dp2Px
import com.somasoma.wagglewaggle.data.Avatar
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import com.somasoma.wagglewaggle.data.model.dto.world.WorldRoom
import com.somasoma.wagglewaggle.databinding.ActivityMainBinding
import com.somasoma.wagglewaggle.presentation.base.BaseActivity
import com.somasoma.wagglewaggle.presentation.follower_following.FollowerFollowingActivity
import com.somasoma.wagglewaggle.presentation.main.create_world.CreateWorldActivity
import com.somasoma.wagglewaggle.presentation.profile.ProfileActivity
import com.somasoma.wagglewaggle.presentation.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    companion object {
        private const val BACKGROUND_SEMICIRCLE_HEIGHT_IN_DP = 295
        private const val BACKGROUND_SEMICIRCLE_HORIZONTAL_MARGIN_IN_DP = 24
        private const val MEMBER = "member"
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val avatarSelectViewPagerAdapter = AvatarSelectPagerAdapter()
    private val worldListAdapter = WorldListAdapter()
    private lateinit var onlineUserListAdapter: OnlineUserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initBinding()
        collect()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.listWorld.adapter = worldListAdapter
        binding.listWorld.isNestedScrollingEnabled = false
        onlineUserListAdapter =  OnlineUserListAdapter(viewModel.onlineUserClickListener)
        binding.listOnline.adapter = onlineUserListAdapter
        initAvatarSelectViewPager()
    }

    private fun initAvatarSelectViewPager() {
        binding.viewpagerSelectCharacter.adapter = avatarSelectViewPagerAdapter
        binding.viewpagerSelectCharacter.isUserInputEnabled = false
        binding.viewpagerSelectCharacter.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.onAvatarSelected(position)
            }
        })
    }

    private fun collect() {
        repeatOnStart { viewModel.eventFlow.collect { handleEvent(it) } }
        repeatOnStart { viewModel.avatars.collect { onAvatarListLoaded(it) } }
        repeatOnStart { viewModel.worlds.collect { onWorldListLoaded(it) } }
        repeatOnStart { viewModel.onlineUsers.collect { onOnlineUserListLoaded(it) } }
        repeatOnStart { viewModel.selectedAvatar.collect { onSelectedAvatarLoaded(it) } }
    }

    private fun handleEvent(event: MainViewModel.Event) = when (event) {
        MainViewModel.Event.NavigateToCreateWorld -> navigateToCreateWorldActivity()
        MainViewModel.Event.NavigateToFollowerFollowing -> navigateToFollowerFollowingActivity()
        MainViewModel.Event.NavigateToSetting -> navigateToSettingActivity()
        is MainViewModel.Event.NavigateToProfile -> navigateToProfileActivity(event.member)
        MainViewModel.Event.ScrollToPrevAvatar -> scrollToPrevAvatar()
        MainViewModel.Event.ScrollToNextAvatar -> scrollToNextAvatar()
    }

    private fun onAvatarListLoaded(avatarList: List<Avatar>) {
        avatarSelectViewPagerAdapter.submitList(avatarList)
    }

    private fun onWorldListLoaded(worldList: List<WorldRoom>) {
        worldListAdapter.submitList(worldList)
    }

    private fun onOnlineUserListLoaded(onlineUsers: List<Member>) {
        onlineUserListAdapter.submitList(onlineUsers)
    }

    private fun onSelectedAvatarLoaded(avatar: Avatar) {
        binding.viewpagerSelectCharacter.currentItem = viewModel.avatars.value?.indexOf(avatar) ?: 0
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

    private fun navigateToProfileActivity(member: Member) {
        val navigateIntent = Intent(this, ProfileActivity::class.java)
        navigateIntent.putExtra(MEMBER, Json.encodeToString(member))
        startActivity(navigateIntent)
    }

    private fun initViewModel() {
        val screenWidth = getScreenWidth()
        viewModel.backgroundSemicircleHeight = calculateBackgroundSemicircleHeight(screenWidth)
        viewModel.backgroundSemicircleRadius = calculateBackgroundSemicircleRadius(screenWidth)
        viewModel.topBarHeight = dp2Px(this, 64f)
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