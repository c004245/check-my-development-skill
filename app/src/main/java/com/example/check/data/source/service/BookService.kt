package com.example.check.data.source.service

import com.example.check.ui.BookListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("books/v1/volumes")
    suspend fun getBookList(@Query("q") title: String, @Query("printType") type: String): BookListModel
}