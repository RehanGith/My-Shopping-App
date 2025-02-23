package com.example.my_shoppings.module

import android.app.Application
import android.content.Context
import com.example.my_shoppings.util.Constant
import com.example.my_shoppings.viewModel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFireStore() = FirebaseFirestore.getInstance()

    @Provides
    fun providesSharedPreference(application: Application) = application.getSharedPreferences(Constant.INTRODUCTION_SP,
        Context.MODE_PRIVATE
    )
}