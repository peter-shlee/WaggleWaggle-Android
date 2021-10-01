package com.somasoma.speakworld.core.di.hilt.module

import com.somasoma.speakworld.domain.auth.sign_up.SignUpViewModel
import com.somasoma.speakworld.domain.custom_views.SelectInterestsViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@InstallIn(FragmentComponent::class)
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindSignUpViewModel(signUpViewModel: SignUpViewModel): SelectInterestsViewModel
}