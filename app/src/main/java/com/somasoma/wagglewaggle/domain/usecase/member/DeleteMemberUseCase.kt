package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class DeleteMemberUseCase @Inject constructor(private val memberRepository: MemberRepository){
    suspend fun deleteMember(unit: Unit) = memberRepository.deleteMember()
}