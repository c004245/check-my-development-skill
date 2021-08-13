package com.example.check.data.source

import com.example.check.ui.BookListModel

interface BookDataSource {
    suspend fun getBookList(): BookListModel
}