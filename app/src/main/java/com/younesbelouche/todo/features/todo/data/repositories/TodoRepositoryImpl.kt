package com.younesbelouche.todo.features.todo.data.repositories

import com.younesbelouche.todo.features.todo.data.datasources.InMemoryTodoDataSource
import com.younesbelouche.todo.features.todo.domain.entities.Task
import com.younesbelouche.todo.features.todo.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val dataSource: InMemoryTodoDataSource
) : TodoRepository {

    override fun getTasks(): Flow<List<Task>> {
        return dataSource.tasks
    }

    override suspend fun addTask(task: Task) {
        dataSource.addTask(task)
    }

    override suspend fun deleteTask(taskId: String) {
        dataSource.deleteTask(taskId)
    }

    override suspend fun toggleTaskCompletion(taskId: String) {
        dataSource.toggleTaskCompletion(taskId)
    }
}

