package com.smparkworld.bigwalktest.ui.donation.di

import androidx.lifecycle.ViewModel
import com.smparkworld.bigwalktest.di.ViewModelKey
import com.smparkworld.bigwalktest.ui.donation.campaign.CampaignViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DonationModule {

    @Binds
    @IntoMap
    @ViewModelKey(CampaignViewModel::class)
    abstract fun bindViewModel(viewModel: CampaignViewModel): ViewModel
}