package com.somasoma.wagglewaggle.domain.repository

import com.somasoma.wagglewaggle.data.model.dto.member.*
import retrofit2.Response

interface MemberRepository {
    suspend fun getMe(): Response<Member?>
    suspend fun getMember(memberId: Int): Response<Member?>
    suspend fun getOnline(): Response<OnlineResponse?>
    suspend fun deleteLogout(): Response<LogoutResponse?>
    suspend fun deleteMember(): Response<DeleteMemberResponse?>
    suspend fun putEditMember(member: Member): Response<Unit?>
    suspend fun getCountryList(): Response<CountryListResponse?>
    suspend fun getLanguageList(): Response<LanguageListResponse?>
    suspend fun getInterestList(): Response<InterestListResponse?>
    suspend fun getNicknameCheck(nickname: String): Response<NicknameCheckResponse?>
}