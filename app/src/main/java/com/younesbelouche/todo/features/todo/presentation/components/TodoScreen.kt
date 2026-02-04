package com.younesbelouche.todo.features.todo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.younesbelouche.todo.core.ui.theme.ToDoTheme
import com.younesbelouche.todo.features.todo.presentation.TodoContract
import com.younesbelouche.todo.features.todo.presentation.TodoViewModel

@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // I intentionally leaved it like this
    // to be able to test is without passing a VM
    TodoScreenContent(
        state = state,
        onEvent = viewModel::handleEvent,
        modifier = modifier
    )
}


@Composable
fun TodoScreenContent(
    state: TodoContract.State,
    onEvent: (TodoContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        TodoHeader()

        AddTask(
            inputText = state.inputText,
            errorMessage = state.errorMessage,
            onInputChange = { onEvent(TodoContract.Event.UpdateInputText(it)) },
            onAddClick = { onEvent(TodoContract.Event.AddTask(state.inputText)) },
            modifier = Modifier.fillMaxWidth()
        )

        ListTitle()

        if (state.tasks.isEmpty()) {
            EmptyTasksState()
        } else {
            TaskList(
                tasks = state.tasks,
                onToggleComplete = { onEvent(TodoContract.Event.ToggleTaskCompletion(it)) },
                onDelete = { onEvent(TodoContract.Event.DeleteTask(it)) },
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TodoScreenPreview_Empty() {
    ToDoTheme(darkTheme = true, dynamicColor = false) {
        TodoScreenContent(
            state = TodoContract.State(
                tasks = emptyList(),
                inputText = "",
                errorMessage = null
            ),
            onEvent = {}
        )
    }
}


