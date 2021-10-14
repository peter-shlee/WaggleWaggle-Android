package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class GetMeUseCase @Inject constructor(private val memberRepository: MemberRepository){
    suspend fun getMe(unit: Unit) = memberRepository.getMe()
}