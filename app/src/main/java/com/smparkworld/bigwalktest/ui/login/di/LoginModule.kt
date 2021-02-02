package com.smparkworld.bigwalktest.ui.login.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.smparkworld.bigwalktest.R
import dagger.Module
import dagger.Provides

@Module
object LoginModule {

    @JvmStatic
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @JvmStatic
    @Provides
    fun provideGoogleSignInOptions(context: Context): GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.firebase_web_client_id))
            .requestEmail()
            .build()
}
