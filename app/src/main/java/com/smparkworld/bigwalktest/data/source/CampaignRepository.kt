package com.smparkworld.bigwalktest.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.smparkworld.bigwalktest.data.Campaign
import com.smparkworld.bigwalktest.data.CampaignType
import com.smparkworld.bigwalktest.data.Result

interface CampaignRepository {

    fun getCampaignsByPaging(type: CampaignType): Result<LiveData<PagedList<Campaign>>>

    fun getParticipatedCampaigns(function: (Result<List<Campaign>>) -> Unit)
}