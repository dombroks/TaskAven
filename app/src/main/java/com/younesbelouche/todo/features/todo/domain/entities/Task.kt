package com.younesbelouche.todo.features.todo.domain.entities

data class Task(
    val id: String,
    val title: String,
    val isCompleted: Boolean = false,
)

