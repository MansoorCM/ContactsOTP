package com.example.contactsotp

import android.util.Base64
import androidx.lifecycle.*
import com.example.contactsotp.data.Contact
import com.example.contactsotp.data.ContactsRepository
import com.example.contactsotp.data.MessageItem
import com.example.contactsotp.network.TwilioApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainViewModel(private val repository: ContactsRepository) : ViewModel() {
    val contactsList: LiveData<List<Contact>> = repository.contactsList.asLiveData()
    val messageItemList: LiveData<List<MessageItem>> = repository.messageItemList.asLiveData()

    // contact to which message will be send, this data will be shared between
    // multiple fragments. Another way to share would be to pass the contact
    // data while navigating between fragments.
    private var _currentContact: Contact? = null
    val currentContact
        get() = _currentContact

    fun setCurrentContact(contact: Contact) {
        _currentContact = contact
    }

    // whenever this changes a snack bar will be shown in the fragment.
    // backing property used to avoid unwanted manipulation of data
    // from outside the class.
    private var _snackBarText = MutableLiveData<String>("")
    val snackBarText: LiveData<String>
        get() = _snackBarText

    fun setSnackBarTextToEmpty() {
        _snackBarText.value = ""
    }

    fun sendMessage(accountSID: String, authToken: String, from: String, otp: Int, body: String) {

        _currentContact?.let {
            val base64EncodedCredentials = getEncodedCredentials(accountSID, authToken)
            val data = getSMSData(from, it.phoneNum, body)

            TwilioApi.retrofitService.sendMessage(accountSID, base64EncodedCredentials, data)
                .enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        successfulResponse(response)
                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                        failureResponse()
                    }
                })
        }
    }

    private fun getEncodedCredentials(accountSID: String, authToken: String) =
        "Basic " + Base64.encodeToString(
            ("$accountSID:$authToken").toByteArray(), Base64.NO_WRAP
        )

    private fun getSMSData(from: String, phoneNum: String, body: String): HashMap<String, String> {
        // a hashmap to hold from, to and body data of the message
        val data: HashMap<String, String> = HashMap()
        data["From"] = from
        data["To"] = phoneNum
        data["Body"] = body
        return data
    }

    private fun successfulResponse(response: Response<ResponseBody?>) {
        if (response.isSuccessful) {
            Timber.d("onResponse->success")
            _snackBarText.value = "SMS Successfully Send."
        } else {
            _snackBarText.value = "SMS Send failed. Make sure phone num is correct and verified."
        }
    }

    private fun failureResponse() {
        _snackBarText.value = "SMS Send failed. Make sure there is proper internet connection."
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