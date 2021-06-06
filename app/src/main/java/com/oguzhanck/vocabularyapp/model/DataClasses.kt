package com.oguzhanck.vocabularyapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "WordDetails")
data class WordEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val ID: Int = 0,
    @ColumnInfo(name = "wordType") val wordType: String,
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "definition") val definition: String,
    @ColumnInfo(name = "synonyms") val synonyms: String,
    @ColumnInfo(name = "antonyms") val antonyms: String,
    @ColumnInfo(name = "exampleSentence") val exampleSentence: String,
)

@Entity(tableName = "ListEntity")
data class ListEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val ID: Int = 0,
    @ColumnInfo(name = "wordType") val wordType: String,
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "definition") val definition: String,
    @ColumnInfo(name = "synonyms") val synonyms: String,
    @ColumnInfo(name = "antonyms") val antonyms: String,
    @ColumnInfo(name = "exampleSentence") val exampleSentence: String,
)