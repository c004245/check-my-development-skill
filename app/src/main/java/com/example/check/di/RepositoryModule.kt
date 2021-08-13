package com.example.check.di

import com.example.check.data.repository.BookRepository
import com.example.check.data.source.BookDataSource
import com.example.check.data.source.BookRemoteDataSource
import com.example.check.data.source.service.BookService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    @Named("remoteBookSource")
    fun provideBookRemoteDataSource(api: BookService): BookDataSource {
        return BookRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideBookRepository(
        @Named("remoteBookSource") bookRemoteDataSource: BookRemoteDataSource
    ): BookRepository {
        return BookRepository(
            bookRemoteDataSource
        )
    }
}