package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.data.repository.MemberRepository
import javax.inject.Inject

class GetLanguageListUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    fun getLanguageList() = memberRepository.getLanguageList()
}