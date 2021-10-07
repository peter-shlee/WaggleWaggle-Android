package com.somasoma.wagglewaggle.domain.repository

import com.somasoma.wagglewaggle.data.model.TmpAvatars

interface AvatarsRepository {
    fun getAvatars(callback: (TmpAvatars) -> Unit)
}