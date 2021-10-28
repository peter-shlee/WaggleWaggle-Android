package com.somasoma.wagglewaggle.data.model.dto.world

import kotlinx.serialization.Serializable

@Serializable
data class WorldRoom(
    val id: Int?,
    val name: String?,
    val topic: String?,
    val map: String?,
    val people: Int?,
    val dateTime: String?,
    val keywords: List<String?>?,
    val photon_server: String?
)
