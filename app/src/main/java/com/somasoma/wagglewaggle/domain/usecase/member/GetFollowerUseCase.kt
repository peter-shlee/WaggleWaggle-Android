package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class GetFollowerUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun getFollower(memberId: Int) = memberRepository.getFollower(memberId)
}