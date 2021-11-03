package com.somasoma.wagglewaggle.presentation.profile

import android.os.Bundle
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.presentation.string2Friendship
import com.somasoma.wagglewaggle.data.Friendship
import com.somasoma.wagglewaggle.databinding.ActivityProfileBinding
import com.somasoma.wagglewaggle.presentation.base.BaseActivity
import com.somasoma.wagglewaggle.presentation.custom_views.SelectedInterestListAdapter
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
    private val adapter = SelectedInterestListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initBinding()

        collect()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initInterestsListRecyclerView()
    }

    private fun initInterestsListRecyclerView() {
        binding.listInterest.adapter = adapter
        setLayoutManager()
    }

    private fun initViewModel() {
        val stringMember = intent.getStringExtra(MEMBER)
        if (!stringMember.isNullOrBlank()) {
            viewModel.member = Json.decodeFromString(stringMember)
            Timber.d(viewModel.member.toString())
        }
    }

    private fun setLayoutManager() {
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        binding.listInterest.layoutManager = layoutManager
    }

    private fun collect() {
        repeatOnStart { viewModel.eventFlow.collect { handleEvent(it) } }
        repeatOnStart { viewModel.interests.collect { onInterestsLoaded(it) } }
    }

    private fun onInterestsLoaded(interests: List<String?>) {
        adapter.submitList(interests)
    }

    private fun handleEvent(event: ProfileViewModel.Event) = when (event) {
        ProfileViewModel.Event.NavigateToPrevPage -> navigateToPrevPage()
        ProfileViewModel.Event.NavigateToUnityWorld -> navigateToUnityWorld()
        ProfileViewModel.Event.OnMenuClicked -> onClickMenu()
    }

    private fun onClickMenu() {
        val popupMenu = initPopupMenu()
        popupMenu.show()
    }

    private fun initPopupMenu(): PopupMenu {
        val popupMenu = PopupMenu(this, binding.btnMenu)
        if (string2Friendship(viewModel.member.friendship) == Friendship.BLOCK) {
            popupMenu.menuInflater.inflate(R.menu.unblock, popupMenu.menu)
        } else {
            popupMenu.menuInflater.inflate(R.menu.block, popupMenu.menu)
        }
        popupMenu.setOnMenuItemClickListener {
            viewModel.onClickMenuItem(it)
            true
        }
        return popupMenu
    }

    private fun navigateToPrevPage() {
        finish()
    }

    private fun navigateToUnityWorld() {
//        val navigateIntent = Intent(this, com.unity3d.player.UnityPlayerActivity::class.java)
//        startActivity(navigateIntent)
    }
}