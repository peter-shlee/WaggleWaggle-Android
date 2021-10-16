package com.somasoma.wagglewaggle.data.model.dto.member

import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val id: Int? = null,
    val nickName: String? = null,
    val country: String? = null,
    val language: String? = null,
    val introduction: String? = null,
    val avatar: String? = null,
    val onlineStatus: String? = null,
    val entranceStatus: String? = null,
    val entranceRoom: String? = null,
    val friendship: String? = null,
    val interests: List<String?>? = null
)
