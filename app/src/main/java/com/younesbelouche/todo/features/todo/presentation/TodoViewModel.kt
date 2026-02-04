package com.younesbelouche.todo.features.todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.younesbelouche.todo.features.todo.domain.usecases.AddTaskUseCase
import com.younesbelouche.todo.features.todo.domain.usecases.DeleteTaskUseCase
import com.younesbelouche.todo.features.todo.domain.usecases.GetTasksUseCase
import com.younesbelouche.todo.features.todo.domain.usecases.ToggleTaskUseCase
import com.younesbelouche.todo.features.todo.presentation.mappers.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val toggleTaskUseCase: ToggleTaskUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TodoContract.State())
    val state: StateFlow<TodoContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<TodoContract.Effect>()
    val effect = _effect.asSharedFlow()

    init {
        observeTasks()
    }

    fun handleEvent(event: TodoContract.Event) {
        when (event) {
            is TodoContract.Event.AddTask -> addTask(event.title)
            is TodoContract.Event.DeleteTask -> deleteTask(event.taskId)
            is TodoContract.Event.ToggleTaskCompletion -> toggleTask(event.taskId)
            is TodoContract.Event.UpdateInputText -> updateInputText(event.text)
            TodoContract.Event.ClearError -> clearError()
        }
    }

    private fun observeTasks() {
        viewModelScope.launch {
            getTasksUseCase().collect { tasks ->
                _state.update { it.copy(tasks = tasks.map { task -> task.toUiModel() }) }
            }
        }
    }

    private fun addTask(title: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            addTaskUseCase(title).fold(
                onSuccess = {
                    _state.update {
                        it.copy(
                            inputText = "",
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                    _effect.emit(TodoContract.Effect.TaskAdded)
                },
                onFailure = { error ->
                    val errorMsg = error.message ?: "Failed to add task"
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = errorMsg
                        )
                    }
                    _effect.emit(TodoContract.Effect.ShowError(errorMsg))
                }
            )
        }
    }

    private fun deleteTask(taskId: String) {
        viewModelScope.launch {
            deleteTaskUseCase(taskId)
        }
    }

    private fun toggleTask(taskId: String) {
        viewModelScope.launch {
            toggleTaskUseCase(taskId)
        }
    }


    private fun updateInputText(text: String) {
        _state.update { it.copy(inputText = text, errorMessage = null) }
    }

    private fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}

