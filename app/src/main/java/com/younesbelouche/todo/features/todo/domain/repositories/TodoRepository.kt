package com.younesbelouche.todo.features.todo.domain.repositories

import com.younesbelouche.todo.features.todo.domain.entities.Task
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTasks(): Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun deleteTask(taskId: String)
    suspend fun toggleTaskCompletion(taskId: String)
}

