package com.somasoma.wagglewaggle.data

enum class DataForUnity {
    INSTANCE;

    var roomId: Int? = null
    var userId: Int? = null
    var avatar: String? = null
    var nickname: String? = null
    var language: String? = null
    var country: String? = null
    var world: String? = null
}

fun setDataForUnity(
    roomId: Int?,
    userId: Int?,
    avatar: String?,
    nickname: String?,
    language: String?,
    country: String?,
    world: String?
) {
    DataForUnity.INSTANCE.roomId = roomId
    DataForUnity.INSTANCE.userId = userId
    DataForUnity.INSTANCE.avatar = avatar?.uppercase()
    DataForUnity.INSTANCE.nickname = nickname
    DataForUnity.INSTANCE.language = language?.uppercase()
    DataForUnity.INSTANCE.country = country?.uppercase()
    DataForUnity.INSTANCE.world = world?.uppercase()
}