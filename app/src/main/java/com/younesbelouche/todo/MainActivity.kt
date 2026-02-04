package com.younesbelouche.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.younesbelouche.todo.features.todo.presentation.components.TodoScreen
import com.younesbelouche.todo.core.ui.theme.ToDoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ToDoTheme(darkTheme = true, dynamicColor = false) {
                Box(modifier = Modifier.fillMaxSize()) {
                    TodoScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}
