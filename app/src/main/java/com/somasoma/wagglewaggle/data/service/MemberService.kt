package com.somasoma.wagglewaggle.data.service

import com.somasoma.wagglewaggle.data.model.dto.member.CountryListResponse
import com.somasoma.wagglewaggle.data.model.dto.member.LanguageListResponse
import com.somasoma.wagglewaggle.data.model.dto.member.LogoutResponse
import com.somasoma.wagglewaggle.data.model.dto.member.OnlineResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET

interface MemberService {
    @GET("member/country-list")
    fun getCountryList(): Single<Response<CountryListResponse>>

    @GET("member/country-list")
    suspend fun getCountryListWithCoroutine(): Response<CountryListResponse>

    @GET("member/language-list")
    fun getLanguageList(): Single<Response<LanguageListResponse>>

    @GET("member/language-list")
    suspend fun getLanguageListWithCoroutine(): Response<LanguageListResponse>

    @GET("member/online")
    fun getOnline(): Single<Response<OnlineResponse>>

    @GET("member/online")
    suspend fun getOnlineWithCoroutine(): Response<OnlineResponse>

    @DELETE("member/logout")
    fun deleteLogout(): Single<Response<LogoutResponse>>

    @DELETE("member/logout")
    suspend fun deleteLogoutWithCoroutine(): Response<LogoutResponse>
}