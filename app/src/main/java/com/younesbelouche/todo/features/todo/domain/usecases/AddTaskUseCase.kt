package com.younesbelouche.todo.features.todo.domain.usecases

import com.younesbelouche.todo.features.todo.domain.entities.Task
import com.younesbelouche.todo.features.todo.domain.repositories.TodoRepository
import java.util.UUID

class AddTaskUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(title: String): Result<Unit> {
        return try {
            if (title.isBlank()) {
                Result.failure(Exception("Title cannot be empty"))
            } else {
                val task = Task(
                    id = UUID.randomUUID().toString(),
                    title = title.trim(),
                    isCompleted = false
                )
                repository.addTask(task)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

