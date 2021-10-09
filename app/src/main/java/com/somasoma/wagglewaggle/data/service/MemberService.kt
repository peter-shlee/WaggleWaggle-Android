package com.somasoma.wagglewaggle.data.service

import com.somasoma.wagglewaggle.data.model.dto.member.CountryListResponse
import com.somasoma.wagglewaggle.data.model.dto.member.LanguageListResponse
import com.somasoma.wagglewaggle.data.model.dto.member.LogoutResponse
import com.somasoma.wagglewaggle.data.model.dto.member.OnlineResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET

interface MemberService {
    @GET("member/country-list")
    suspend fun getCountryList(): Response<CountryListResponse>

    @GET("member/language-list")
    suspend fun getLanguageList(): Response<LanguageListResponse>

    @GET("member/online")
    suspend fun getOnline(): Response<OnlineResponse>

    @DELETE("member/logout")
    suspend fun deleteLogout(): Response<LogoutResponse>
}