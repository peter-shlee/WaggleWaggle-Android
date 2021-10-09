package com.somasoma.wagglewaggle.domain.usecase.member

import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import javax.inject.Inject

class DeleteLogoutUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    fun deleteLogout() = memberRepository.deleteLogout()
    suspend fun deleteLogoutWithCoroutine(unit: Unit) = memberRepository.deleteLogoutWithCoroutine()
}