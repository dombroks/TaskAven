package com.younesbelouche.todo.features.todo.domain.usecases

import com.younesbelouche.todo.core.util.Result
import com.younesbelouche.todo.features.todo.domain.repositories.TodoRepository

class DeleteTaskUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(taskId: String): Result<Unit> {
        return repository.deleteTask(taskId)
    }
}

