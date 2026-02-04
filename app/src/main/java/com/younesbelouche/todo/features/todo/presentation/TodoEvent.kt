package com.younesbelouche.todo.features.todo.presentation

sealed interface Event {
    data class AddTask(val title: String) : Event
    data class DeleteTask(val taskId: String) : Event
    data class ToggleTaskCompletion(val taskId: String) : Event
    data class UpdateInputText(val text: String) : Event
    object ClearError : Event
}