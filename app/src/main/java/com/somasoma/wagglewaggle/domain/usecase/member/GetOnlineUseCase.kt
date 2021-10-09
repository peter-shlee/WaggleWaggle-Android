package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class GetOnlineUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun getOnline(unit: Unit) = memberRepository.getOnline()
}