package com.smparkworld.bigwalktest.data.source.remote

import android.content.SharedPreferences
import com.smparkworld.bigwalktest.data.Campaign
import com.smparkworld.bigwalktest.data.Result
import com.smparkworld.bigwalktest.data.Result.Error
import com.smparkworld.bigwalktest.data.Result.Success
import com.smparkworld.bigwalktest.data.source.remote.api.CampaignService
import retrofit2.Callback

class CampaignRemoteDataSourceImpl (
    private val campaignService: CampaignService,
    private val sharedPref: SharedPreferences
) : CampaignRemoteDataSource {

    init {
        // 테스트를 위한 초기 Token 값
        sharedPref.edit().putString("token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxODUiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjExNTYzMzgxLCJleHAiOjE3MDYxNzEzODF9._4DPRRFx09yIBVLqwbTGVSuP6vy5fM4UP3vJXszfP4w").apply()
    }

    override fun getCampaigns(page: Int, size: Int): Result<List<Campaign>> {
        val token = sharedPref.getString("token", null)
        val response = campaignService.getCampaignList(token!!, page, size).execute()    // 초기 Token 값이 있다고 가정

        return if (response.isSuccessful && response.code() == 200 && response.body() != null) {
            Success(response.body()!!)
        } else {
            Error(Exception("원격 데이터로부터 로드 실패"))
        }
    }

    override fun getCampaigns(callback: Callback<List<Campaign>>) {
        val token = sharedPref.getString("token", null)
        campaignService.getCampaignList(token!!, 0, 60).enqueue(callback)    // 초기 Token 값이 있다고 가정
    }
}