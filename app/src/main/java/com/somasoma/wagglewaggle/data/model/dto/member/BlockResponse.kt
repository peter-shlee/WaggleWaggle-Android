package com.somasoma.wagglewaggle.data.model.dto.member

data class BlockResponse(
    val status: String?
) {
    companion object {
        const val OK = "ok"
        const val FAIL = "fail"
    }
}
