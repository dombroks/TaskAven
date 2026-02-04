package com.younesbelouche.todo.features.todo.data.repositories

import com.younesbelouche.todo.core.util.Result
import com.younesbelouche.todo.features.todo.data.datasources.InMemoryTodoDataSource
import com.younesbelouche.todo.features.todo.data.mappers.toDomain
import com.younesbelouche.todo.features.todo.data.mappers.toDto
import com.younesbelouche.todo.features.todo.data.models.TaskDto
import com.younesbelouche.todo.features.todo.domain.entities.Task
import com.younesbelouche.todo.features.todo.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class TodoRepositoryImpl(
    private val dataSource: InMemoryTodoDataSource
) : TodoRepository {

    override fun getTasks(): Flow<Result<List<Task>>> =
        dataSource.tasks
            .map<List<TaskDto>, Result<List<Task>>> { dtos ->
                Result.Success(dtos.map { it.toDomain() })
            }
            .onStart {
                emit(Result.Loading)
            }
            .catch { e ->
                emit(Result.Error(e))
            }

    override suspend fun addTask(task: Task): Result<Unit> {
        return try {
            dataSource.addTask(task.toDto())
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteTask(taskId: String): Result<Unit> {
        return try {
            dataSource.deleteTask(taskId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun toggleTaskCompletion(taskId: String): Result<Unit> {
        return try {
            dataSource.toggleTaskCompletion(taskId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

