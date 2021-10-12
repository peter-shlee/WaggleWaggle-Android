package com.somasoma.wagglewaggle.data.repository_impl

import com.somasoma.wagglewaggle.core.di.hilt.qualifier.ForMemberAPI
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import com.somasoma.wagglewaggle.data.service.MemberService
import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import retrofit2.Retrofit
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    @ForMemberAPI private val memberRetrofit: Retrofit
) : MemberRepository {

    private val memberService = memberRetrofit.create(MemberService::class.java)

    override suspend fun getCountryList() = memberService.getCountryList()
    override suspend fun getLanguageList() = memberService.getLanguageList()
    override suspend fun getInterestList() = memberService.getInterestList()
    override suspend fun getOnline() = memberService.getOnline()
    override suspend fun deleteLogout() = memberService.deleteLogout()
    override suspend fun deleteMember() = memberService.deleteMember()
    override suspend fun putEditMember(member: Member) = memberService.putEditMember(member)
}