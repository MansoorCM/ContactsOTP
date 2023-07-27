package com.example.contactsotp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class MessageItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "contactName") val contactName: String,
    @ColumnInfo(name = "otp") val otp: Int,
    @ColumnInfo(name = "timeOfCreation") val timeOfCreation: Long = System.currentTimeMillis(),
)