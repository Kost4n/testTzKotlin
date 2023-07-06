package com.kost4n.testandroidapp.roof.database

import android.content.Context
import androidx.room.*
import com.kost4n.testandroidapp.roof.conventer.RecordConverter
import com.kost4n.testandroidapp.roof.dao.RecordDao
import com.kost4n.testandroidapp.roof.entity.Record

@Database(
    version = 1,
    entities = [
        Record::class
    ],
    exportSchema = true
)
@TypeConverters(RecordConverter::class)
abstract class RecordDatabase: RoomDatabase() {
    abstract fun getRecordDao(): RecordDao

    companion object {
        @Volatile
        private var INSTANCE: RecordDatabase? = null

        fun getDatabase(context: Context): RecordDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): RecordDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                RecordDatabase::class.java,
                "record_database"
            ).build()
        }
    }
}