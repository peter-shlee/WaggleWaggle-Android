package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class GetFollowingUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun getFollowing(memberId: Int) = memberRepository.getFollowing(memberId)
}