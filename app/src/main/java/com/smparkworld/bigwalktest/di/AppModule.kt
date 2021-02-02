package com.smparkworld.bigwalktest.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    private const val SP_NAME = "sp_bigwalk"

    @JvmStatic
    @Singleton
    @Provides
    fun providePref(context: Context): SharedPreferences {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

}