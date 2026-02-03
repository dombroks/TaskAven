package com.younesbelouche.todo.features.todo.data.repositories

import com.younesbelouche.todo.features.todo.data.datasources.InMemoryTodoDataSource
import com.younesbelouche.todo.features.todo.data.mappers.toDomain
import com.younesbelouche.todo.features.todo.data.mappers.toDto
import com.younesbelouche.todo.features.todo.domain.entities.Task
import com.younesbelouche.todo.features.todo.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dataSource: InMemoryTodoDataSource
) : TodoRepository {

    override fun getTasks(): Flow<List<Task>> {
        return dataSource.tasks.map { dtoList ->
            dtoList.map { it.toDomain() }
        }
    }

    override suspend fun addTask(task: Task) {
        dataSource.addTask(task.toDto())
    }

    override suspend fun deleteTask(taskId: String) {
        dataSource.deleteTask(taskId)
    }

    override suspend fun toggleTaskCompletion(taskId: String) {
        dataSource.toggleTaskCompletion(taskId)
    }
}

