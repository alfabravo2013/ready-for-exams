package com.github.alfabravo2013.readyforexams

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ReadyForExamApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ReadyForExamApp)
        }
    }
}
