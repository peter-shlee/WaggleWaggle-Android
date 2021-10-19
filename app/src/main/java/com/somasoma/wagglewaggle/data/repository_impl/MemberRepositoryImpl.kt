package com.somasoma.wagglewaggle.data.repository_impl

import com.somasoma.wagglewaggle.core.di.hilt.qualifier.ForMemberAPI
import com.somasoma.wagglewaggle.data.model.dto.member.Member
import com.somasoma.wagglewaggle.data.service.MemberService
import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import retrofit2.Retrofit
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    @ForMemberAPI private val retrofits: Pair<Retrofit, Retrofit>
) : MemberRepository {

    private val memberService = retrofits.first.create(MemberService::class.java)
    private val publicMemberService = retrofits.second.create(MemberService::class.java)

    override suspend fun getMe() = memberService.getMe()
    override suspend fun getMember(memberId: Int) = memberService.getMember(memberId)
    override suspend fun getOnline() = memberService.getOnline()
    override suspend fun getFollowing(memberId: Int) = memberService.getFollowing(memberId)
    override suspend fun getFollower(memberId: Int) = memberService.getFollower(memberId)
    override suspend fun deleteLogout() = memberService.deleteLogout()
    override suspend fun deleteMember() = memberService.deleteMember()
    override suspend fun putEditMember(member: Member) = memberService.putEditMember(member)

    override suspend fun getCountryList() = publicMemberService.getCountryList()
    override suspend fun getLanguageList() = publicMemberService.getLanguageList()
    override suspend fun getInterestList() = publicMemberService.getInterestList()
    override suspend fun getNicknameCheck(nickname: String) = publicMemberService.getNicknameCheck(nickname)
}