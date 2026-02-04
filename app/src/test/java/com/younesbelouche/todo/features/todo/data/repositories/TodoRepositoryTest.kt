package com.younesbelouche.todo.features.todo.data.repositories

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.younesbelouche.todo.core.util.Result
import com.younesbelouche.todo.features.todo.data.datasources.InMemoryTodoDataSource
import com.younesbelouche.todo.features.todo.data.mappers.toDomain
import com.younesbelouche.todo.features.todo.data.models.TaskDto
import com.younesbelouche.todo.features.todo.domain.repositories.TodoRepository
import io.mockk.coEvery
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TodoRepositoryTest {
    @get:Rule(order = 0)
    val mockkRule = MockKRule(this)

    private lateinit var sut: TodoRepository

    private val inMemoryTodoDataSource: InMemoryTodoDataSource = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    private val taskDtoList = listOf<TaskDto>(
        TaskDto(id = "1", title = "Task 1", isCompleted = false),
        TaskDto(id = "2", title = "Task 2", isCompleted = false),
    )
    private val taskList = taskDtoList.map { it.toDomain() }

    @Before
    fun setUp() {
        sut = TodoRepositoryImpl(inMemoryTodoDataSource, dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getTasks should return tasks`() = runTest {
        val tasksStateFlow = MutableStateFlow(taskDtoList)
        coEvery { inMemoryTodoDataSource.tasks } returns tasksStateFlow

        val result = sut.getTasks()

        result.test {
            val loadingItem = awaitItem()
            assertThat(loadingItem).isEqualTo(Result.Loading)

            val successItem = awaitItem() as Result.Success
            assertThat(successItem.data).isEqualTo(taskList)

            cancelAndIgnoreRemainingEvents()
        }
    }
}