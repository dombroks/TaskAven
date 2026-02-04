package com.younesbelouche.todo.features.todo.data.datasources

import com.younesbelouche.todo.features.todo.data.models.TaskDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.emptyList

class InMemoryTodoDataSource {

    private var _tasks = mutableListOf<TaskDto>()

    fun getTasks(): List<TaskDto> {
        return _tasks.toList()
    }

    fun addTask(task: TaskDto) {
        _tasks += task
    }

    fun deleteTask(taskId: String) {
        _tasks = _tasks.filter { it.id != taskId }.toMutableList()
    }

    fun toggleTaskCompletion(taskId: String) {
        _tasks = _tasks.map { task ->
            if (task.id == taskId) {
                task.copy(isCompleted = !task.isCompleted)
            } else {
                task
            }
        } as MutableList<TaskDto>
    }
}

