package com.example.contactsotp

import androidx.lifecycle.*

class MainViewModel() : ViewModel() {

}

// view model factory used to pass parameters while creating the view model.
class MainViewModelFactory() :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}