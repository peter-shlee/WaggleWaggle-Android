package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class DeleteLogoutUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun deleteLogout(unit: Unit) = memberRepository.deleteLogout()
}