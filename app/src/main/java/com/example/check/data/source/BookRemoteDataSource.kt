package com.example.check.data.source

import com.example.check.data.source.service.BookService
import com.example.check.ui.BookListModel
import javax.inject.Inject

class BookRemoteDataSource @Inject constructor(private val api: BookService): BookDataSource {
    override suspend fun getBookList(): BookListModel = api.getBookList("android", "books")
}