package com.somasoma.wagglewaggle.core.usecase

import com.somasoma.wagglewaggle.core.repository.MemberRepository
import javax.inject.Inject

class GetCountryListUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    fun getCountryList() = memberRepository.getCountryList()
}