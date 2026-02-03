package com.younesbelouche.todo.features.todo.presentation.models

data class TaskUiModel(
    val id: String,
    val title: String,
    val isCompleted: Boolean = false,
)

