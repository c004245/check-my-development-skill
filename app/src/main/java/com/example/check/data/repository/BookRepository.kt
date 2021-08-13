package com.example.check.data.repository

import com.example.check.data.source.BookDataSource
import com.example.check.ui.BookListModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository @Inject constructor(
    private val remote: BookDataSource
) {
    suspend fun getBookList(): BookListModel {
        return remote.getBookList()
    }
}