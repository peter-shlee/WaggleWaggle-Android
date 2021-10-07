package com.somasoma.wagglewaggle.domain.repository

import com.somasoma.wagglewaggle.data.model.Avatars

interface AvatarsRepository {
    fun getAvatars(callback: (Avatars) -> Unit)
}