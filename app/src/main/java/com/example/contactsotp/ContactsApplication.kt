package com.example.contactsotp

import android.app.Application
import com.example.contactsotp.data.ContactsDatabase
import com.example.contactsotp.data.ContactsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

class ContactsApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { ContactsDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ContactsRepository(database.contactDao()) }
    override fun onCreate() {
        super.onCreate()

        // Timber for logging.
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}