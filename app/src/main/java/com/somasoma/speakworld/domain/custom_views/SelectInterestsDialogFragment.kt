package com.somasoma.speakworld.domain.custom_views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.somasoma.speakworld.R
import com.somasoma.speakworld.databinding.FragmentSetInterestsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectInterestsDialogFragment : BottomSheetDialogFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = SelectInterestsDialogFragment()
    }

    private val activityViewModel: SelectInterestsViewModel by activityViewModels()
    private val viewModel: SelectInterestsDialogViewModel by viewModels()
    private lateinit var binding: FragmentSetInterestsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_set_interests,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        observe()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observe() {
        viewModel.closeDialogEvent.observe(this) {
            dismiss()
        }
    }
}