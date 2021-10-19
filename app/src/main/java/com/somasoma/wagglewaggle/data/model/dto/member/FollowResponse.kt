package com.somasoma.wagglewaggle.data.model.dto.member

data class FollowResponse(
    val status: String?
) {
    companion object {
        const val OK = "ok"
        const val NO = "no"
    }
}