package com.oguzhanck.vocabularyapp.local

import androidx.lifecycle.LiveData
import com.oguzhanck.vocabularyapp.model.ListEntity
import com.oguzhanck.vocabularyapp.model.WordEntity

class WordRepository(private val wordDao: WordDao) {

    val allWordDetail: LiveData<List<WordEntity>> = wordDao.findAll()
    val allWordDetailList: LiveData<List<ListEntity>> = wordDao.findAllList()

    fun insertWordDetail(wordEntity: WordEntity) {
        wordDao.insert(wordEntity)
    }

    fun deleteWordDetail(wordEntity: WordEntity) {
        wordDao.delete(wordEntity)
    }

    // LIST
    fun insertWordDetailList(listEntity: ListEntity) {
        wordDao.insertList(listEntity)
    }

    fun deleteWordDetailList(listEntity: ListEntity) {
        wordDao.deleteList(listEntity)
    }

    fun checkEmptyList(): Int {
        return wordDao.checkEmptyList()
    }
}