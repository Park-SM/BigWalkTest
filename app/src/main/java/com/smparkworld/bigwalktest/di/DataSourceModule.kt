package com.smparkworld.bigwalktest.di

import android.content.SharedPreferences
import com.smparkworld.bigwalktest.data.source.remote.CampaignRemoteDataSourceImpl
import com.smparkworld.bigwalktest.data.source.remote.api.CampaignService
import com.smparkworld.bigwalktest.data.source.remote.CampaignRemoteDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object DataSourceModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideCampaignRemoteDataSource(
        retrofitClient: Retrofit,
        sharedPref: SharedPreferences
    ): CampaignRemoteDataSource =
        CampaignRemoteDataSourceImpl(
                retrofitClient.create(CampaignService::class.java),
                sharedPref
        )

}