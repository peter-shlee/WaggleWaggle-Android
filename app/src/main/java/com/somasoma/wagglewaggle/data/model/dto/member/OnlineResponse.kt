package com.somasoma.wagglewaggle.data.model.dto.member

data class OnlineResponse(
    val onlineFollwoingMemberSize: Int?,
    val onlineFollowingMembers: List<Member?>?,
    val onlineMemberSize: Int?,
    val onlineMembers: List<Member?>?
)
