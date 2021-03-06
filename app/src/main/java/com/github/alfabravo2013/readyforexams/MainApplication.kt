package com.github.alfabravo2013.readyforexams

import android.app.Application
import com.github.alfabravo2013.readyforexams.di.appModule
import com.github.alfabravo2013.readyforexams.di.homeModule
import com.github.alfabravo2013.readyforexams.di.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(appModule, loginModule, homeModule))
        }
    }
}
