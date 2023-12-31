package com.kost4n.testandroidapp.roof.conventer

import androidx.room.TypeConverter
import java.util.*

class RecordConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? {
        return date?.time
    }
}