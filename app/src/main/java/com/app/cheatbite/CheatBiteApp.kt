package com.app.cheatbite

import android.app.Application
import com.app.cheatbite.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CheatBiteApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CheatBiteApp)
            androidLogger()
            modules(appModule)
        }
    }
}