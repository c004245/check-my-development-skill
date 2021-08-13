package com.example.check.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor() : ViewModel() {
    /**
     * UseCase
     */
    private val book
}


data class BookListModel(
    val items: List<BookModel>
)

data class BookModel(
    val volumeInfo: BookVolumnInfo
)

data class BookVolumnInfo(
    val imageLink: BookImageLink,
    val title: String,
    val description: String,
    val authors: List<BookAuthorModel>

)

data class BookImageLink(
    val thumbnail: String
)

data class BookAuthorModel(
    val authors: String
)