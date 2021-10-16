package com.somasoma.wagglewaggle.presentation.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import javax.inject.Inject

class ProfileViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    var member: Member = Member()
}