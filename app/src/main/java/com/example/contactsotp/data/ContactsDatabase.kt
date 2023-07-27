package com.example.contactsotp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.contactsotp.util.readAssetsFile
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Contact::class, MessageItem::class], version = 1, exportSchema = false)
public abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    private class ContactsDatabaseCallback(
        private val context: Context,
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val timerDao = database.contactDao()

                    // read default contacts from json
                    val items = Gson().fromJson(
                        context.assets.readAssetsFile("defaultContacts.json"),
                        ContactList::class.java
                    )

                    // insert contacts into the database
                    for (contact in items.contacts) {
                        timerDao.insert(contact)
                    }

                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ContactsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsDatabase::class.java,
                    "contacts_database"
                )
                    .addCallback(ContactsDatabaseCallback(context, scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}