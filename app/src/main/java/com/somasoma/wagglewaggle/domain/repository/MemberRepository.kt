package com.somasoma.wagglewaggle.domain.repository

import com.somasoma.wagglewaggle.data.model.dto.auth.DeleteMemberResponse
import com.somasoma.wagglewaggle.data.model.dto.member.CountryListResponse
import com.somasoma.wagglewaggle.data.model.dto.member.LanguageListResponse
import com.somasoma.wagglewaggle.data.model.dto.member.LogoutResponse
import com.somasoma.wagglewaggle.data.model.dto.member.OnlineResponse
import retrofit2.Response

interface MemberRepository {
    suspend fun getCountryList(): Response<CountryListResponse>
    suspend fun getLanguageList(): Response<LanguageListResponse>
    suspend fun getOnline(): Response<OnlineResponse>
    suspend fun deleteLogout(): Response<LogoutResponse>
    suspend fun deleteMember(): Response<DeleteMemberResponse>
}