package com.example.check.domain

import com.example.check.ui.BookListModel
import com.example.check.ui.BookModel
import com.example.check.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class BookListUseCase @Inject constructor(
    private val bookRepo: BookRepository,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
): UseCase <Unit, BookListModel>(ioDispatcher){

    override suspend fun CoroutineScope.execute(parameters: Unit): Result<BookListModel> {

            val bookListModel = bookRepo.getBookList()

        }
    }
}