package com.somasoma.wagglewaggle.data.repository_impl

import com.somasoma.wagglewaggle.core.di.hilt.qualifier.ForMemberAPI
import com.somasoma.wagglewaggle.data.model.dto.member.OnlineResponse
import com.somasoma.wagglewaggle.data.service.MemberService
import com.somasoma.wagglewaggle.domain.repository.MemberRepository
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    @ForMemberAPI private val memberRetrofit: Retrofit
) : MemberRepository {

    private val memberService = memberRetrofit.create(MemberService::class.java)

    override fun getCountryList() = memberService.getCountryList()
    override fun getLanguageList() = memberService.getLanguageList()
    override fun getOnline(): Single<Response<OnlineResponse>> = memberService.getOnline()
}