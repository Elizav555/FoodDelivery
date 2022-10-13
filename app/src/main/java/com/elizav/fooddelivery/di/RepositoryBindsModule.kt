package com.elizav.fooddelivery.di

import com.elizav.fooddelivery.data.MealsRepositoryImpl
import com.elizav.fooddelivery.domain.MealsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindsModule {
    @Binds
    @Singleton
    abstract fun bindMealsRepository(
        mealsRepositoryImpl: MealsRepositoryImpl
    ): MealsRepository
}