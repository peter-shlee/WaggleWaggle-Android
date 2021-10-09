package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class GetOnlineUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    fun getOnline() = memberRepository.getOnline()
    suspend fun getOnlineWithCoroutine(unit: Unit) = memberRepository.getOnlineWithCoroutine()
}