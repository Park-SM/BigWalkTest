package com.smparkworld.bigwalktest.di

import com.smparkworld.bigwalktest.data.source.CampaignRepository
import com.smparkworld.bigwalktest.data.source.repository.CampaignRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: CampaignRepositoryImpl): CampaignRepository
}