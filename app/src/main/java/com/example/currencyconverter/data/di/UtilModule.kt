package com.example.currencyconverter.data.di

import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus

@Module
object UtilModule {

    @Provides
    fun provideEventBus(): EventBus {
        return EventBus.getDefault()
    }
}