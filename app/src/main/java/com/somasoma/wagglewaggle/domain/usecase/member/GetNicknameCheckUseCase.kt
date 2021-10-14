package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class GetNicknameCheckUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun getNicknameCheck(nickname: String) = memberRepository.getNicknameCheck(nickname)
}