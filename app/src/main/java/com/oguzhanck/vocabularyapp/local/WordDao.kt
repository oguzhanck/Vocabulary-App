package com.oguzhanck.vocabularyapp.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.oguzhanck.vocabularyapp.model.ListEntity
import com.oguzhanck.vocabularyapp.model.WordEntity

@Dao
interface WordDao {
    @Query("SELECT * FROM WordDetails ORDER BY word ASC")
    fun findAll(): LiveData<List<WordEntity>>

    @Delete
    fun delete(wordEntity: WordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wordEntity: WordEntity)

    @Query("SELECT * FROM ListEntity ORDER BY word ASC")
    fun findAllList(): LiveData<List<ListEntity>>

    @Delete
    fun deleteList(listEntity: ListEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(listEntity: ListEntity)

    @Query("SELECT COUNT(*) FROM ListEntity")
    fun checkEmptyList(): Int
}