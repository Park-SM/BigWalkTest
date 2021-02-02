package com.smparkworld.bigwalktest.ui.donation.campaign

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.smparkworld.bigwalktest.data.Campaign
import com.smparkworld.bigwalktest.data.CampaignType
import com.smparkworld.bigwalktest.data.Result.Success
import com.smparkworld.bigwalktest.data.source.CampaignRepository
import javax.inject.Inject

class CampaignViewModel @Inject constructor(
    private val campaignRepository: CampaignRepository
) : ViewModel() {

    lateinit var mCampaigns: LiveData<PagedList<Campaign>>

    val mParticipatedCampaigns = MutableLiveData<List<Campaign>>()

    fun loadCampaignsByPaging(owner: LifecycleOwner, adapter: CampaignListAdapter?, type: CampaignType): Boolean {
        val campaigns = campaignRepository.getCampaignsByPaging(type)
        return if (campaigns is Success) {
            if (::mCampaigns.isInitialized) mCampaigns.removeObservers(owner)
            mCampaigns = campaigns.data
            mCampaigns.observe(owner) { adapter?.submitList(it) }
            true
        } else false
    }

    fun loadParticipatedCampaigns() {
        campaignRepository.getParticipatedCampaigns {
            if (it is Success) {
                mParticipatedCampaigns.value = it.data
            }
        }
    }
}