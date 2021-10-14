package com.somasoma.wagglewaggle.data.model.dto.auth

data class SignUpResponse(
    val isNewMember: String?,
    val memberId: Long?,
    val accessToken: String?,
    val refreshToken: String?,
    val grantType: String?,
    val accessTokenExpiresIn: Long?
)
