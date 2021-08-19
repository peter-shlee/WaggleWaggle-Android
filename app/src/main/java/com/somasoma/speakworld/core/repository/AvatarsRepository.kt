package com.somasoma.speakworld.core.repository

import com.somasoma.speakworld.core.model.Avatars

interface AvatarsRepository {
    fun getAvatars(callback: (Avatars) -> Unit)
}