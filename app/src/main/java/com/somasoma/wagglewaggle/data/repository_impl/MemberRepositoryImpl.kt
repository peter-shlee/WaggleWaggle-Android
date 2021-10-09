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
    override suspend fun getCountryListWithCoroutine() = memberService.getCountryListWithCoroutine()
    override fun getLanguageList() = memberService.getLanguageList()
    override suspend fun getLanguageListWithCoroutine() = memberService.getLanguageListWithCoroutine()
    override fun getOnline(): Single<Response<OnlineResponse>> = memberService.getOnline()
    override suspend fun getOnlineWithCoroutine() = memberService.getOnlineWithCoroutine()
    override fun deleteLogout() = memberService.deleteLogout()
    override suspend fun deleteLogoutWithCoroutine() = memberService.deleteLogoutWithCoroutine()
}