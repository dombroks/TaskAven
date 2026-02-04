package com.younesbelouche.todo.features.todo.presentation

import com.younesbelouche.todo.features.todo.presentation.models.TaskUiModel
import java.util.Collections

data class State(
    val tasks: List<TaskUiModel> = Collections.emptyList(),
    val inputText: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
