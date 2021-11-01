package com.somasoma.wagglewaggle.presentation.main.create_world

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.somasoma.wagglewaggle.R
import com.somasoma.wagglewaggle.data.WorldMap
import com.somasoma.wagglewaggle.databinding.ActivityCreateWorldBinding
import com.somasoma.wagglewaggle.presentation.base.BaseActivity
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsDialogFragment
import com.somasoma.wagglewaggle.presentation.custom_views.SelectedInterestListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CreateWorldActivity : BaseActivity() {

    private val viewModel: CreateWorldViewModel by viewModels()
    private lateinit var binding: ActivityCreateWorldBinding
    private val selectedInterestListAdapter = SelectedInterestListAdapter()
    private val mapListAdapter = MapListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_world)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.listKeyword.adapter = selectedInterestListAdapter
        binding.listMap.adapter = mapListAdapter
        setLayoutManager()

        collect()
    }

    private fun setLayoutManager() {
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        binding.listKeyword.layoutManager = layoutManager
    }

    private fun collect() {
        repeatOnStart { viewModel.eventFlow.collect { handleEvent(it) } }
        repeatOnStart { viewModel.selectedInterests.collect { onSelectedInterestsChanged(it) } }
        repeatOnStart { viewModel.maps.collect { onMapListLoaded(it) } }
    }

    private fun handleEvent(event: CreateWorldViewModel.Event) = when (event) {
        CreateWorldViewModel.Event.NavigateToPrevPage -> navigateToPrevPage()
        CreateWorldViewModel.Event.ShowSelectInterestsDialog -> showSelectInterestsDialog()
        CreateWorldViewModel.Event.NavigateToUnityWorld -> navigateToUnityWorld()
    }

    private fun navigateToUnityWorld() {
//        val navigateIntent = Intent(this, com.unity3d.player.UnityPlayerActivity::class.java)
//        startActivity(navigateIntent)
        finish()
    }

    private fun navigateToPrevPage() {
        finish()
    }

    private fun showSelectInterestsDialog() {
        val createWorldSelectInterestsDialogFragment =
            CreateWorldSelectInterestDialogFragment.newInstance()
        createWorldSelectInterestsDialogFragment.show(
            supportFragmentManager,
            SelectInterestsDialogFragment::class.java.simpleName
        )
    }

    private fun onSelectedInterestsChanged(selectedInterests: Set<String>) {
        selectedInterestListAdapter.submitList(selectedInterests.toList())
    }

    private fun onMapListLoaded(mapList: List<WorldMap>) {
        mapListAdapter.submitList(mapList)
    }
}