package com.example.contactsotp

import android.app.Application
import timber.log.Timber

class ContactsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Timber for logging.
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}