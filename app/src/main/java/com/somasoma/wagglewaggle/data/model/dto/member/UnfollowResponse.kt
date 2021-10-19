package com.somasoma.wagglewaggle.data.model.dto.member

data class UnfollowResponse(
    val status: String?
) {
    companion object {
        const val OK = "ok"
        const val NO = "no"
    }
}
