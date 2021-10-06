package com.somasoma.wagglewaggle.core.repository

import com.somasoma.wagglewaggle.core.model.dto.member.CountryListResponse
import com.somasoma.wagglewaggle.core.model.dto.member.LanguageListResponse
import io.reactivex.Single
import retrofit2.Response

interface MemberRepository {
    fun getCountryList(): Single<Response<CountryListResponse>>
    fun getLanguageList(): Single<Response<LanguageListResponse>>
}