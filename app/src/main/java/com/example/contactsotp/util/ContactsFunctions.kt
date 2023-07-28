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

/*
generates a six digit random number.
preferred way in kotlin is (100000..999999).random(), but
sometimes that is returning the same random number.
*/
fun getOTP(): Int {
    val start = 100000
    val end = 999999
    return Random().nextInt(end + 1 - start) + start
}

// extension function to read data from Json.
fun AssetManager.readAssetsFile(fileName: String): String =
    open(fileName).bufferedReader().use { it.readText() }