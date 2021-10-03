package com.somasoma.wagglewaggle.core.repository

import com.somasoma.wagglewaggle.core.model.Avatars

interface AvatarsRepository {
    fun getAvatars(callback: (Avatars) -> Unit)
}