package com.somasoma.wagglewaggle.data.model.dto.member

data class UnblockResponse(
    val status: String?
) {
    companion object {
        const val OK = "ok"
        const val NO_BLOCKING = "no blocking"
    }
}