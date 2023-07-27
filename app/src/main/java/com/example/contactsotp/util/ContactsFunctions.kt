package com.example.contactsotp.util

import android.content.res.AssetManager
import android.text.format.DateFormat
import java.util.*

// converts timestamp to proper date-time string.
fun getDate(timestamp: Long): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = timestamp
    return DateFormat.format("yyyy-MM-dd HH:mm:ss", calendar).toString()
}

// extension function to read data from Json.
fun AssetManager.readAssetsFile(fileName: String): String =
    open(fileName).bufferedReader().use { it.readText() }