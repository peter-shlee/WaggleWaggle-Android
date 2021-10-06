package com.somasoma.wagglewaggle.data.model.dto.auth

data class RefreshRequest(
    val accessToken: String,
    val refreshToken: String
)
