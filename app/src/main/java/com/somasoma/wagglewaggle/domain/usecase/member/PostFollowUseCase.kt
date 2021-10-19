package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class PostFollowUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun postFollow(memberId: Int) = memberRepository.postFollow(memberId)
}