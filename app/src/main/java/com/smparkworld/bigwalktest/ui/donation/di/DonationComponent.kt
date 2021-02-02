package com.smparkworld.bigwalktest.ui.donation.di

import com.smparkworld.bigwalktest.ui.donation.campaign.CampaignListFragment
import com.smparkworld.bigwalktest.ui.donation.campaign.CampaignFragment
import dagger.Subcomponent

@Subcomponent(modules = [DonationModule::class])
interface DonationComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create() : DonationComponent
    }

    fun inject(campaignFragment: CampaignFragment)
    fun inject(campaignListFragment: CampaignListFragment)
}