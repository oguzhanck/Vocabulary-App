package com.oguzhanck.vocabularyapp.local

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.oguzhanck.vocabularyapp.model.ListEntity
import com.oguzhanck.vocabularyapp.model.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(application: Application) : AndroidViewModel(application) {

    val allWordDetail: LiveData<List<WordEntity>>
    val allWordDetailList: LiveData<List<ListEntity>>
    private val repository: WordRepository

    init {
        val wordDao = LocalDatabase.getAppDatabase(application)?.wordDao()
        repository = WordRepository(wordDao!!)
        allWordDetail = repository.allWordDetail
        allWordDetailList = repository.allWordDetailList
    }

    fun insertWordDetail(wordEntity: WordEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertWordDetail(wordEntity)
        }
    }

    fun deleteWordDetail(wordEntity: WordEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWordDetail(wordEntity)
        }
    }

    // LIST
    fun insertWordDetailList(listEntity: ListEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertWordDetailList(listEntity)
        }
    }

    fun deleteWordDetailList(listEntity: ListEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWordDetailList(listEntity)
        }
    }

    fun checkEmptyList(): Int {
        return repository.checkEmptyList()
    }

}