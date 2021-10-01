package com.somasoma.speakworld.domain.auth.sign_up

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.somasoma.speakworld.domain.custom_views.SelectInterestsDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpSelectInterestsDialogFragment: SelectInterestsDialogFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = SignUpSelectInterestsDialogFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val signUpViewModel: SignUpViewModel by activityViewModels()
        setViewModel(signUpViewModel)
        super.onCreate(savedInstanceState)
    }
}