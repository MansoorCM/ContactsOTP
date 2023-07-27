package com.example.contactsotp.data

import kotlinx.coroutines.flow.Flow

class ContactsRepository(private val contactDao: ContactDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val contactsList: Flow<List<Contact>> = contactDao.getContacts()

    val messageItemList: Flow<List<MessageItem>> = contactDao.getMessages()

    suspend fun insertMessage(messageItem: MessageItem) {
        contactDao.insertMessage(messageItem)
    }
}