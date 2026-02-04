package com.younesbelouche.todo.features.todo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.younesbelouche.todo.core.ui.theme.ToDoTheme
import com.younesbelouche.todo.features.todo.presentation.TodoContract
import com.younesbelouche.todo.features.todo.presentation.TodoViewModel
import com.younesbelouche.todo.features.todo.presentation.models.TaskUiModel

@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

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
        TodoHeader(modifier = modifier)

        AddTask(
            inputText = state.inputText,
            errorMessage = state.errorMessage,
            onInputChange = { onEvent(TodoContract.Event.UpdateInputText(it)) },
            onAddClick = { onEvent(TodoContract.Event.AddTask(state.inputText)) },
            modifier = Modifier.fillMaxWidth()
        )

        Title()

        if (state.tasks.isEmpty()) {
            EmptyTasksState(modifier = modifier)
        } else {
            TaskList(
                tasks = state.tasks,
                onToggleComplete = { onEvent(TodoContract.Event.ToggleTaskCompletion(it)) },
                onDelete = { onEvent(TodoContract.Event.DeleteTask(it)) },
            )
        }
    }
}

@Composable
private fun Title() {
    Text(
        text = "À FAIRE",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(bottom = 16.dp, top = 32.dp)
    )
}

@Composable
private fun TaskList(
    tasks: List<TaskUiModel>,
    onToggleComplete: (String) -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = tasks,
            key = { task -> task.id }
        ) { task ->
            TaskItem(
                task = task,
                onToggleComplete = { onToggleComplete(task.id) },
                onDelete = { onDelete(task.id) }
            )
        }
    }
}

@Composable
private fun EmptyTasksState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Aucune tâche pour le moment!\nAjoutez votre première tâche ci-dessus.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TodoHeader(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Gestion des Tâches",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 4.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "MINIMALISTE V1",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )
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


