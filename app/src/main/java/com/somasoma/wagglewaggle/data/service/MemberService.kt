package com.somasoma.wagglewaggle.data.service

import com.somasoma.wagglewaggle.data.model.dto.member.*
import retrofit2.Response
import retrofit2.http.*

interface MemberService {

    @GET("member/me")
    suspend fun getMe(): Response<Member?>

    @GET("member/{memberId}")
    suspend fun getMember(@Path("memberId") memberId: Int): Response<Member?>

    @GET("member/online")
    suspend fun getOnline(): Response<OnlineResponse?>

    @DELETE("member/logout")
    suspend fun deleteLogout(): Response<LogoutResponse?>

    @DELETE("member/{memberID}")
    suspend fun deleteMember(@Path("memberID") memberId: Int = -1): Response<DeleteMemberResponse?>

    @PUT("member/edit-member")
    suspend fun putEditMember(@Body member: Member): Response<Unit?>


    @GET("member/basics/country-list")
    suspend fun getCountryList(): Response<CountryListResponse?>

    @GET("member/basics/language-list")
    suspend fun getLanguageList(): Response<LanguageListResponse?>

    @GET("member/basics/interest-list")
    suspend fun getInterestList(): Response<InterestListResponse?>

    @GET("member/basics/nickname-check")
    suspend fun getNicknameCheck(@Query("nickname") nickname: String): Response<NicknameCheckResponse?>
}