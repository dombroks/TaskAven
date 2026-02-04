package com.younesbelouche.todo.features.todo.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.younesbelouche.todo.core.ui.theme.ToDoTheme
import com.younesbelouche.todo.features.todo.presentation.components.AddTask
import com.younesbelouche.todo.features.todo.presentation.components.EmptyTasksState
import com.younesbelouche.todo.features.todo.presentation.components.ListTitle
import com.younesbelouche.todo.features.todo.presentation.components.TaskList
import com.younesbelouche.todo.features.todo.presentation.components.TodoHeader

@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
    state: State,
    onEvent: (Event) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            onEvent(Event.ClearError)
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            TodoHeader()

            AddTask(
                inputText = state.inputText,
                onInputChange = { onEvent(Event.UpdateInputText(it)) },
                onAddClick = { onEvent(Event.AddTask(state.inputText)) },
                modifier = Modifier.fillMaxWidth()
            )

            ListTitle()

            if (state.tasks.isEmpty()) {
                EmptyTasksState()
            } else {
                TaskList(
                    tasks = state.tasks,
                    onToggleComplete = { onEvent(Event.ToggleTaskCompletion(it)) },
                    onDelete = { onEvent(Event.DeleteTask(it)) },
                )
            }
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TodoScreenPreview_Empty() {
    ToDoTheme(darkTheme = true, dynamicColor = false) {
        TodoScreenContent(
            state = State(
                tasks = emptyList(),
                inputText = "",
                errorMessage = null
            ),
            onEvent = {}
        )
    }
}


