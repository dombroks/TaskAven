package com.younesbelouche.todo.features.todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.younesbelouche.todo.core.util.Result
import com.younesbelouche.todo.features.todo.domain.usecases.AddTaskUseCase
import com.younesbelouche.todo.features.todo.domain.usecases.DeleteTaskUseCase
import com.younesbelouche.todo.features.todo.domain.usecases.GetTasksUseCase
import com.younesbelouche.todo.features.todo.domain.usecases.ToggleTaskUseCase
import com.younesbelouche.todo.features.todo.presentation.mappers.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()


    init {
        loadTasks()
    }

    fun handleEvent(event: Event) {
        when (event) {
            is Event.AddTask -> addTask(event.title)
            is Event.DeleteTask -> deleteTask(event.taskId)
            is Event.ToggleTaskCompletion -> toggleTask(event.taskId)
            is Event.UpdateInputText -> updateInputText(event.text)
            Event.ClearError -> clearError()
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            getTasksUseCase().collect { result ->
                when (result) {
                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }

                    Result.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                tasks = result.data.map { it.toUiModel() },
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun addTask(title: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }


            when (val result = addTaskUseCase(title)) {
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }

                Result.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            inputText = "",
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    private fun deleteTask(taskId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = deleteTaskUseCase(taskId)) {
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }

                Result.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            inputText = "",
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    private fun toggleTask(taskId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = toggleTaskUseCase(taskId)) {
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }

                Result.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            inputText = "",
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }


    private fun updateInputText(text: String) {
        _state.update { it.copy(inputText = text, errorMessage = null) }
    }

    private fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}

