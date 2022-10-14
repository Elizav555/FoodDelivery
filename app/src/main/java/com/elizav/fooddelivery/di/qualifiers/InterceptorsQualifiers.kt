package com.elizav.fooddelivery.di.qualifiers

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class HostInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OfflineInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class OnlineInterceptor