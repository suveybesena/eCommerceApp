package com.example.capstoneproject.di

import com.example.capstoneproject.data.repository.FirebaseAuthRepositoryImpl
import com.example.capstoneproject.data.repository.LocalRepositoryImpl
import com.example.capstoneproject.data.repository.RemoteRepositoryImpl
import com.example.capstoneproject.domain.repository.FirebaseAuthRepository
import com.example.capstoneproject.domain.repository.LocalRepository
import com.example.capstoneproject.domain.repository.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRemoteRepository(remoteRepository: RemoteRepositoryImpl): RemoteRepository

    @Binds
    @Singleton
    abstract fun bindLocalRepository(localRepository: LocalRepositoryImpl): LocalRepository

    @Binds
    @Singleton
    abstract fun bindFirebaseAuthRepository(firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl): FirebaseAuthRepository
}