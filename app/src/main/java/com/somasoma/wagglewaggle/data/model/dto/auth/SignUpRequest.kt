package com.somasoma.wagglewaggle.data.model.dto.auth

data class SignUpRequest(
    val firebaseToken: String,
    val nickname: String,
    val country: String,
    val language: String,
    val introduction: String,
    val interests: List<String>
)
