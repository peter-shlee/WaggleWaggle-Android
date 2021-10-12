package com.somasoma.wagglewaggle.presentation.setting

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileSelectInterestsDialogFragment: SelectInterestsDialogFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = EditProfileSelectInterestsDialogFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val editProfileViewModel: EditProfileViewModel by activityViewModels()
        setViewModel(editProfileViewModel)
        super.onCreate(savedInstanceState)
    }
}