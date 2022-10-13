package com.elizav.fooddelivery.di

import com.elizav.fooddelivery.domain.interactors.MealsInteractor
import com.elizav.fooddelivery.domain.interactors.MealsInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class InteractorsBindsModule {
    @Binds
    abstract fun bindMealsInteractor(
        mealsInteractorImpl: MealsInteractorImpl
    ): MealsInteractor
}