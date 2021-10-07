package com.somasoma.wagglewaggle.data.repository.impl

import com.somasoma.wagglewaggle.core.di.hilt.qualifier.ForMemberAPI
import com.somasoma.wagglewaggle.data.repository.MemberRepository
import com.somasoma.wagglewaggle.data.service.MemberService
import retrofit2.Retrofit
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    @ForMemberAPI private val memberRetrofit: Retrofit
) : MemberRepository {

    private val memberService = memberRetrofit.create(MemberService::class.java)

    override fun getCountryList() = memberService.getCountryList()
    override fun getLanguageList() = memberService.getLanguageList()
}