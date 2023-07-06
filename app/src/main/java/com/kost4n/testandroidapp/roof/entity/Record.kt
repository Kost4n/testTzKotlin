package com.kost4n.testandroidapp.roof.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "records"
)
data class Record(
    @PrimaryKey
    @ColumnInfo(name = "date") val date: Date,
    @Embedded
    val workout: Workout
)