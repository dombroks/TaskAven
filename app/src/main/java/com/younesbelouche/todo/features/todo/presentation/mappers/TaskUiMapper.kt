package com.younesbelouche.todo.features.todo.presentation.mappers

import com.younesbelouche.todo.features.todo.domain.entities.Task
import com.younesbelouche.todo.features.todo.presentation.models.TaskUiModel

fun Task.toUiModel(): TaskUiModel {
    return TaskUiModel(
        id = id,
        title = title,
        isCompleted = isCompleted
    )
}

fun TaskUiModel.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        isCompleted = isCompleted
    )
}

