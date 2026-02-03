package com.younesbelouche.todo.features.todo.di

import com.younesbelouche.todo.features.todo.domain.repositories.TodoRepository
import com.younesbelouche.todo.features.todo.domain.usecases.AddTaskUseCase
import com.younesbelouche.todo.features.todo.domain.usecases.DeleteTaskUseCase
import com.younesbelouche.todo.features.todo.domain.usecases.GetTasksUseCase
import com.younesbelouche.todo.features.todo.domain.usecases.ToggleTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoDomainModule {

    @Provides
    @Singleton
    fun provideGetTasksUseCase(
        repository: TodoRepository
    ): GetTasksUseCase {
        return GetTasksUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddTaskUseCase(
        repository: TodoRepository
    ): AddTaskUseCase {
        return AddTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTaskUseCase(
        repository: TodoRepository
    ): DeleteTaskUseCase {
        return DeleteTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideToggleTaskUseCase(
        repository: TodoRepository
    ): ToggleTaskUseCase {
        return ToggleTaskUseCase(repository)
    }
}

