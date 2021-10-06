package com.somasoma.wagglewaggle.core.usecase

import com.somasoma.wagglewaggle.core.repository.MemberRepository
import javax.inject.Inject

class GetLanguageListUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    fun getLanguageList() = memberRepository.getLanguageList()
}