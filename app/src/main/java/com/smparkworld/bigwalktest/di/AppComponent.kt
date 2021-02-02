package com.smparkworld.bigwalktest.di

import android.content.Context
import com.smparkworld.bigwalktest.data.source.CampaignRepository
import com.smparkworld.bigwalktest.ui.donation.di.DonationComponent
import com.smparkworld.bigwalktest.ui.login.di.LoginComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppModuleBinds::class,
        DataSourceModule::class,
        NetworkModule::class,
        ViewModelBuilderModule::class,
        SubcomponentModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun loginComponent(): LoginComponent.Factory
    fun donationComponent(): DonationComponent.Factory

    val campaignRepository: CampaignRepository
}

@Module(
    subcomponents = [
        LoginComponent::class,
        DonationComponent::class
    ]
)
object SubcomponentModule