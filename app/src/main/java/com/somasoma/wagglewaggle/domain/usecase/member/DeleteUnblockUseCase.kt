package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class DeleteUnblockUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun deleteUnblock(memberId: Int) = memberRepository.deleteUnblock(memberId)
}