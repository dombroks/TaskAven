package com.younesbelouche.todo.features.todo.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.younesbelouche.todo.features.todo.presentation.models.TaskUiModel

@Composable
fun TaskList(
    tasks: List<TaskUiModel>,
    onToggleComplete: (String) -> Unit,
    onDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(
            tasks, key = TaskUiModel::id
        ) { task ->
            TaskItem(
                task = task,
                onToggleComplete = { onToggleComplete(task.id) },
                onDelete = { onDelete(task.id) }
            )
        }
    }
}