package com.somasoma.speakworld.domain.custom_views

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.somasoma.speakworld.R
import com.somasoma.speakworld.databinding.FragmentSetInterestsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
open class SelectInterestsDialogFragment : BottomSheetDialogFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = SelectInterestsDialogFragment()
    }

    private  var activityViewModel: SelectInterestsViewModel? = null
    private val viewModel: SelectInterestsDialogViewModel by viewModels()
    private lateinit var binding: FragmentSetInterestsBinding

    private lateinit var adapter: InterestListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activityViewModel == null) {
            val selectInterestsViewModel: SelectInterestsViewModel by activityViewModels()
            setViewModel(selectInterestsViewModel)
        }
        initViewModel()
        observe()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBinding(inflater, container)
        return binding.root
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_set_interests,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setAdapter()
        setLayoutManager()
    }

    fun setViewModel(viewModel: SelectInterestsViewModel) {
        activityViewModel = viewModel
    }

    private fun setAdapter() {
        initAdapter()
        binding.listInterests.adapter = adapter
    }

    private fun initAdapter() {
        adapter = InterestListAdapter(
            ::onInterestKeywordClicked,
            viewModel
        )
    }

    private fun setLayoutManager() {
        val layoutManager = FlexboxLayoutManager(this.context)
        layoutManager.flexDirection = FlexDirection.ROW
        binding.listInterests.layoutManager = layoutManager
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        activityViewModel?.let {
            val selectedInterests = viewModel.getSelectedInterests()
            it.resetSelectedInterests(selectedInterests)
        }
    }

    private fun initViewModel() {
        activityViewModel?.let {
            val selectedInterests = it.getSelectedInterests()
            viewModel.setSelectedInterests(selectedInterests)
        }
    }

    private fun observe() {
        viewModel.closeDialogEvent.observe(this) { dismiss() }
        viewModel.interests.observe(this) { onInterestsLoaded(it) }
    }

    private fun onInterestsLoaded(interestsSet: Set<String>) {
        adapter.submitList(interestsSet.toList())
        Timber.d(interestsSet.toString())
    }

    private fun onInterestKeywordClicked(interestKeyword: String, isSelected: Boolean) {
        viewModel.onInterestKeywordClicked(interestKeyword, isSelected)
    }
}