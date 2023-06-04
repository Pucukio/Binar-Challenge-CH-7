package com.pucuk.binar_challenge_ch_7.data.di

import com.pucuk.binar_challenge_ch_7.data.repository.LocalRepository
import com.pucuk.binar_challenge_ch_7.data.repository.LocalRepositoryImpl
import com.pucuk.binar_challenge_ch_7.data.repository.NetworkRepository
import com.pucuk.binar_challenge_ch_7.data.repository.NetworkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideNetworkRepository(networkRepositoryImpl: NetworkRepositoryImpl): NetworkRepository

    @Binds
    abstract fun provideLocalRepository(localRepositoryImpl: LocalRepositoryImpl): LocalRepository

}