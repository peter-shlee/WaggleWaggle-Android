package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class GetLanguageListUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun getLanguageList(unit: Unit) = memberRepository.getLanguageList()
}