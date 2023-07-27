package com.example.contactsotp

import androidx.lifecycle.*
import com.example.contactsotp.data.Contact
import com.example.contactsotp.data.ContactsRepository

class MainViewModel(private val repository: ContactsRepository) : ViewModel() {
    val contactsList: LiveData<List<Contact>> = repository.contactsList.asLiveData()

    // contact to which message will be send, this data will be shared between
    // multiple fragments. Another way to share would be to pass the contact
    // data while navigating between fragments.
    private var _currentContact: Contact? = null
    val currentContact
        get() = _currentContact

    fun setCurrentContact(contact: Contact) {
        _currentContact = contact
    }

}

// view model factory used to pass parameters while creating the view model.
class MainViewModelFactory(private val repository: ContactsRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}