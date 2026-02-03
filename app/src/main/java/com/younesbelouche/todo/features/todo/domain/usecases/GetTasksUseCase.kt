package com.younesbelouche.todo.features.todo.domain.usecases

import com.younesbelouche.todo.features.todo.domain.entities.Task
import com.younesbelouche.todo.features.todo.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetTasksUseCase(private val repository: TodoRepository) {
    operator fun invoke(): Flow<List<Task>> {
        return repository.getTasks()
    }
}

