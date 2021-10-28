package com.somasoma.wagglewaggle.data

import com.somasoma.wagglewaggle.core.avatar2String

enum class DataForUnity {
    INSTANCE;

    var roomId: String? = null
    var userId: Int? = null
    var avatar: String? = null
    var language: String? = null
    var country: String? = null
    var world: String? = null
}

fun setDataForUnity(
    roomId: String,
    userId: Int,
    avatar: Avatar,
    language: String,
    country: String,
    world: String
) {
    DataForUnity.INSTANCE.roomId = roomId
    DataForUnity.INSTANCE.userId = userId
    DataForUnity.INSTANCE.avatar = avatar2String(avatar).uppercase()
    DataForUnity.INSTANCE.language = language.uppercase()
    DataForUnity.INSTANCE.country = country.uppercase()
    DataForUnity.INSTANCE.world = world.uppercase()
}