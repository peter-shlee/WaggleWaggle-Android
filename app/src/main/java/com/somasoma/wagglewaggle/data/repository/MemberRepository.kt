package com.somasoma.wagglewaggle.data.repository

import com.somasoma.wagglewaggle.data.model.dto.member.CountryListResponse
import com.somasoma.wagglewaggle.data.model.dto.member.LanguageListResponse
import io.reactivex.Single
import retrofit2.Response

interface MemberRepository {
    fun getCountryList(): Single<Response<CountryListResponse>>
    fun getLanguageList(): Single<Response<LanguageListResponse>>
}