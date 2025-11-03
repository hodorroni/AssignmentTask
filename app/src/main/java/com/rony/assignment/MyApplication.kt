package com.rony.assignment

import android.app.Application
import com.rony.assignment.core.data.di.coreDataModule
import com.rony.assignment.di.appModule
import com.rony.assignment.features.auth.data.di.firebaseModule
import com.rony.assignment.features.auth.presentation.login.di.authPresentationModule
import com.rony.assignment.features.auth.presentation.register.di.registerPresentationModule
import com.rony.assignment.features.notes.domain.di.noteDomainModule
import com.rony.assignment.features.notes.presentation.di.allNotesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@MyApplication)
            modules(
                appModule,
                authPresentationModule,
                registerPresentationModule,
                firebaseModule,
                coreDataModule,
                allNotesModule,
                noteDomainModule
            )
        }
    }
}