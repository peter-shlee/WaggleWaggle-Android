package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class PostBlockUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun postBlock(memberId: Int) = memberRepository.postBlock(memberId)
}