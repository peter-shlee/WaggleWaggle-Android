package com.somasoma.wagglewaggle.domain.repository

import com.somasoma.wagglewaggle.data.model.dto.member.CountryListResponse
import com.somasoma.wagglewaggle.data.model.dto.member.LanguageListResponse
import com.somasoma.wagglewaggle.data.model.dto.member.OnlineResponse
import io.reactivex.Single
import retrofit2.Response

interface MemberRepository {
    fun getCountryList(): Single<Response<CountryListResponse>>
    fun getLanguageList(): Single<Response<LanguageListResponse>>
    fun getOnline(): Single<Response<OnlineResponse>>
}