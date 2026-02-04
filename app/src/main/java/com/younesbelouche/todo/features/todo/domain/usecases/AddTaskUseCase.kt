package com.younesbelouche.todo.features.todo.domain.usecases

import com.younesbelouche.todo.features.todo.domain.entities.Task
import com.younesbelouche.todo.features.todo.domain.repositories.TodoRepository
import java.util.UUID
import com.younesbelouche.todo.core.util.Result

class AddTaskUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(title: String): Result<Unit> {
        if (title.isBlank()) {
            return Result.Error(IllegalArgumentException("Title cannot be blank"))
        }

        val task = Task(
            id = UUID.randomUUID().toString(),
            title = title.trim(),
            isCompleted = false
        )
        return repository.addTask(task)
    }
}

