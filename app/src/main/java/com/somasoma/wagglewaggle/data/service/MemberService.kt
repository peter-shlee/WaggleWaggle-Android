package com.somasoma.wagglewaggle.data.service

import com.somasoma.wagglewaggle.data.model.dto.member.CountryListResponse
import com.somasoma.wagglewaggle.data.model.dto.member.LanguageListResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface MemberService {
    @GET("member/country-list")
    fun getCountryList(): Single<Response<CountryListResponse>>

    @GET("member/language-list")
    fun getLanguageList(): Single<Response<LanguageListResponse>>
}