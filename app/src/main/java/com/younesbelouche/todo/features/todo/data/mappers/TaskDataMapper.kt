package com.younesbelouche.todo.features.todo.data.mappers

import com.younesbelouche.todo.features.todo.data.models.TaskDto
import com.younesbelouche.todo.features.todo.domain.entities.Task

fun TaskDto.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        isCompleted = isCompleted
    )
}

fun Task.toDto(): TaskDto {
    return TaskDto(
        id = id,
        title = title,
        isCompleted = isCompleted
    )
}

