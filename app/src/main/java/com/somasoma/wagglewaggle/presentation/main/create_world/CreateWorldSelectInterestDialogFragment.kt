package com.somasoma.wagglewaggle.presentation.main.create_world

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.somasoma.wagglewaggle.presentation.custom_views.SelectInterestsDialogFragment

class CreateWorldSelectInterestDialogFragment: SelectInterestsDialogFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = CreateWorldSelectInterestDialogFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val createWorldViewModel: CreateWorldViewModel by activityViewModels()
        setViewModel(createWorldViewModel)
        super.onCreate(savedInstanceState)
    }
}