package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class GetInterestListUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun getInterestList(unit: Unit) = memberRepository.getInterestList()
}