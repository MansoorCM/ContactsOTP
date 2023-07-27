package com.example.contactsotp.util

import android.content.res.AssetManager

// extension function to read data from Json.
fun AssetManager.readAssetsFile(fileName: String): String =
    open(fileName).bufferedReader().use { it.readText() }