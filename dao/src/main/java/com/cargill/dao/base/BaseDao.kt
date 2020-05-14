package com.cargill.dao.base

import androidx.room.*

@Dao
interface BaseDao<T> {

    // insert single
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertorReplace(obj: T?): Long

    // insert List
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllorReplace(obj: List<T>?) : List<Long>

    // update List
    @Update
    suspend fun update(obj: List<T>?): Int

    @Delete
    suspend fun delete(obj: T?):Int

}