package com.kost4n.testandroidapp.roof.dao

import androidx.room.*
import com.kost4n.testandroidapp.roof.entity.Record
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecord(recordEntity: Record)

    @Query("SELECT * FROM records ORDER BY date DESC")
    fun getRecords(): Flow<List<Record>>

    @Query("DELETE FROM records WHERE date = :recordDate")
    fun deleteByRecordDate(recordDate: Date)
}