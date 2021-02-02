package com.smparkworld.bigwalktest.data.source.remote

import com.smparkworld.bigwalktest.data.Campaign
import com.smparkworld.bigwalktest.data.Result
import retrofit2.Callback

interface CampaignRemoteDataSource {

    fun getCampaigns(page: Int, size: Int): Result<List<Campaign>>

    fun getCampaigns(callback: Callback<List<Campaign>>)

}