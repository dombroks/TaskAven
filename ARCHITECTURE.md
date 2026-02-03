# Project Architecture

This project follows **Clean Architecture** principles, organized by **feature** (also known as feature-first or vertical slice architecture).

## Directory Structure

```
app/src/main/java/com/younesbelouche/todo/
├── MainActivity.kt
├── core/
│   └── ui/
│       └── theme/
│           ├── Color.kt
│           ├── Theme.kt
│           └── Type.kt
└── features/
    └── todo/
        ├── data/
        │   ├── datasources/
        │   │   └── InMemoryTodoDataSource.kt
        │   └── repositories/
        │       └── TodoRepositoryImpl.kt
        ├── domain/
        │   ├── entities/
        │   │   └── Task.kt
        │   ├── repositories/
        │   │   └── TodoRepository.kt
        │   └── usecases/
        │       ├── AddTaskUseCase.kt
        │       ├── DeleteTaskUseCase.kt
        │       ├── GetTasksUseCase.kt
        │       └── ToggleTaskUseCase.kt
        └── presentation/
            ├── components/
            │   ├── AddTaskSection.kt
            │   ├── TaskItem.kt
            │   └── TodoScreen.kt
            ├── TodoContract.kt
            └── TodoViewModel.kt
```

## Architecture Layers

### 1. **Core Module** (`core/`)
Contains shared resources used across multiple features:
- **UI Theme**: Color schemes, typography, Material Design theme configurations

### 2. **Features Module** (`features/`)
Each feature is self-contained with its own layers:

#### **Todo Feature** (`features/todo/`)

##### **Domain Layer** (`domain/`)
- **Entities**: Core business models (`Task`)
- **Repositories**: Interface definitions for data operations (`TodoRepository`)
- **Use Cases**: Business logic operations
  - `GetTasksUseCase`: Retrieve all tasks
  - `AddTaskUseCase`: Add a new task with validation
  - `DeleteTaskUseCase`: Delete a task by ID
  - `ToggleTaskUseCase`: Toggle task completion status

##### **Data Layer** (`data/`)
- **Data Sources**: Data access implementations (`InMemoryTodoDataSource`)
- **Repositories**: Implementation of domain repository interfaces (`TodoRepositoryImpl`)

##### **Presentation Layer** (`presentation/`)
- **MVI Pattern**: Implements Model-View-Intent architecture
  - `TodoContract`: Defines State, Events, and Effects
  - `TodoViewModel`: Manages UI state and handles user events
- **Components**: Reusable Composable UI components
  - `TodoScreen`: Main screen composable
  - `AddTaskSection`: Input field and add button
  - `TaskItem`: Individual task row display

## Benefits of Feature-Based Architecture

1. **Modularity**: Each feature is independent and can be developed/tested in isolation
2. **Scalability**: Easy to add new features without affecting existing ones
3. **Team Collaboration**: Different teams can work on different features simultaneously
4. **Code Organization**: Related code stays together, making it easier to understand
5. **Testability**: Features can be tested independently with clear boundaries
6. **Maintainability**: Changes to one feature don't impact others
7. **Reusability**: Core utilities are shared while feature-specific code is isolated

## Design Patterns Used

- **Clean Architecture**: Separation of concerns with clear dependency rules
- **MVI (Model-View-Intent)**: Unidirectional data flow for presentation layer
- **Repository Pattern**: Abstraction layer for data access
- **Use Case Pattern**: Single responsibility business logic operations
- **Dependency Injection**: Manual DI in MainActivity (ready for Hilt/Koin)

## Future Enhancements

As the project grows, you can:
- Add more features under `features/` (e.g., `features/settings/`, `features/statistics/`)
- Extract common domain logic to `core/domain/`
- Add `core/data/` for shared data utilities
- Add `core/network/` for API clients
- Implement proper DI framework (Hilt recommended)
- Add feature modules as separate Gradle modules for even better isolation

