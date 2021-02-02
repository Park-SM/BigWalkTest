package com.smparkworld.bigwalktest.data.source.remote.api

import com.smparkworld.bigwalktest.data.Campaign
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CampaignService {

    @GET("/api/campaigns/category/0/story")
    fun getCampaignList(
        @Header("X-AUTH-TOKEN") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<List<Campaign>>
}