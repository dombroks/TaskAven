package com.younesbelouche.todo.features.todo.data.models

data class TaskDto(
    val isCompleted: Boolean = false,
    val title: String,
    val id: String,
)


