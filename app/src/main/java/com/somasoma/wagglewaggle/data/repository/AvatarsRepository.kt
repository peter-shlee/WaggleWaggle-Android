package com.somasoma.wagglewaggle.data.repository

import com.somasoma.wagglewaggle.data.model.Avatars

interface AvatarsRepository {
    fun getAvatars(callback: (Avatars) -> Unit)
}