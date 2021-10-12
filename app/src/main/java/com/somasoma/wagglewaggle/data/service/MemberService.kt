package com.somasoma.wagglewaggle.data.service

import com.somasoma.wagglewaggle.data.model.dto.member.*
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface MemberService {
    @GET("member/country-list")
    suspend fun getCountryList(): Response<CountryListResponse>

    @GET("member/language-list")
    suspend fun getLanguageList(): Response<LanguageListResponse>

    @GET("member/interest-list")
    suspend fun getInterestList(): Response<InterestListResponse>

    @GET("member/online")
    suspend fun getOnline(): Response<OnlineResponse>

    @DELETE("member/logout")
    suspend fun deleteLogout(): Response<LogoutResponse>

    @DELETE("member/{memberID}")
    suspend fun deleteMember(@Path("memberID") memberId: Int = -1): Response<DeleteMemberResponse>

    @PUT("member/edit-member")
    suspend fun putEditMember(member: Member): Response<Unit>
}