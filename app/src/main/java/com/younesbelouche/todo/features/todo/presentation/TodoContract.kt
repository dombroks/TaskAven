package com.younesbelouche.todo.features.todo.presentation

import com.younesbelouche.todo.features.todo.presentation.models.TaskUiModel
import java.util.Collections.emptyList

object TodoContract {

    data class State(
        val tasks: List<TaskUiModel> = emptyList(),
        val inputText: String = "",
        val errorMessage: String? = null,
        val isLoading: Boolean = false
    )

    sealed interface Event {
        data class AddTask(val title: String) : Event
        data class DeleteTask(val taskId: String) : Event
        data class ToggleTaskCompletion(val taskId: String) : Event
        data class UpdateInputText(val text: String) : Event
        object ClearError : Event
    }

    sealed interface Effect {
        data class ShowError(val message: String) : Effect
        object TaskAdded : Effect
    }
}

