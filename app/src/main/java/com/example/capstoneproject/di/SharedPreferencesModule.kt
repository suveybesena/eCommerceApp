package com.example.capstoneproject.di

import android.content.Context
import android.content.SharedPreferences
import com.example.capstoneproject.common.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(Constant.SET_SHARED_PREF_KEY, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideUserId(sharedPreferences: SharedPreferences): String =
        sharedPreferences.getString(Constant.SHARED_PREF_KEY, null).toString()

}