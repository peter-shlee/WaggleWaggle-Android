package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.data.model.dto.member.Member
import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class PutEditMemberUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun putEditMember(member: Member) = memberRepository.putEditMember(member)
}