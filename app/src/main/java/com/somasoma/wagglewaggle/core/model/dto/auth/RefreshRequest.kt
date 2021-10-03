package com.somasoma.wagglewaggle.core.model.dto.auth

data class RefreshRequest(
    val accessToken: String,
    val refreshToken: String
)
