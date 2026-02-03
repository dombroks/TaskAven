package com.younesbelouche.todo.features.todo.di

import com.younesbelouche.todo.features.todo.data.datasources.InMemoryTodoDataSource
import com.younesbelouche.todo.features.todo.data.repositories.TodoRepositoryImpl
import com.younesbelouche.todo.features.todo.domain.repositories.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideInMemoryTodoDataSource(): InMemoryTodoDataSource {
        return InMemoryTodoDataSource()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(
        dataSource: InMemoryTodoDataSource
    ): TodoRepository {
        return TodoRepositoryImpl(dataSource)
    }
}

