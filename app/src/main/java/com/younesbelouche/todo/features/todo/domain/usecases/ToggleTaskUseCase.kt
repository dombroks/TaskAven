package com.younesbelouche.todo.features.todo.domain.usecases

import com.younesbelouche.todo.core.util.Result
import com.younesbelouche.todo.features.todo.domain.repositories.TodoRepository

class ToggleTaskUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(taskId: String) = repository.toggleTaskCompletion(taskId)

}

