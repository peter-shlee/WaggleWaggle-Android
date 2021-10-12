package com.somasoma.wagglewaggle.data.model.dto.auth

data class RefreshResponse(
    val accessToken: String?,
    val accessTokenExpiresIn: Long?
)