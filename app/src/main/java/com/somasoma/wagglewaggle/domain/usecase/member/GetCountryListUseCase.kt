package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class GetCountryListUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun getCountryList(unit: Unit) = memberRepository.getCountryList()
}