package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class DeleteUnfollowUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun deleteUnfollow(memberId: Int) = memberRepository.deleteUnfollow(memberId)
}