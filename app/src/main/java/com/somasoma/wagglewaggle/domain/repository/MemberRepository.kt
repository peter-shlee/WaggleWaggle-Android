package com.somasoma.wagglewaggle.domain.repository

import com.somasoma.wagglewaggle.data.model.dto.member.*
import retrofit2.Response

interface MemberRepository {
    suspend fun getCountryList(): Response<CountryListResponse>
    suspend fun getLanguageList(): Response<LanguageListResponse>
    suspend fun getInterestList(): Response<InterestListResponse>
    suspend fun getOnline(): Response<OnlineResponse>
    suspend fun deleteLogout(): Response<LogoutResponse>
    suspend fun deleteMember(): Response<DeleteMemberResponse>
}