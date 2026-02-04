package com.younesbelouche.todo.features.todo.domain.repositories

import com.younesbelouche.todo.core.util.Result
import com.younesbelouche.todo.features.todo.domain.entities.Task
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTasks(): Flow<Result<List<Task>>>
    suspend fun addTask(task: Task): Result<Unit>
    suspend fun deleteTask(taskId: String): Result<Unit>
    suspend fun toggleTaskCompletion(taskId: String): Result<Unit>
}

