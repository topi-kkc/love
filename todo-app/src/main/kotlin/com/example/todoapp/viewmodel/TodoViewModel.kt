package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Priority
import com.example.todoapp.data.TodoItem
import com.example.todoapp.data.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    
    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoList: StateFlow<List<TodoItem>> = _todoList.asStateFlow()
    
    private val _activeTodoCount = MutableStateFlow(0)
    val activeTodoCount: StateFlow<Int> = _activeTodoCount.asStateFlow()
    
    private val _filterType = MutableStateFlow(FilterType.ALL)
    val filterType: StateFlow<FilterType> = _filterType.asStateFlow()
    
    private val _newTodoTitle = MutableStateFlow("")
    val newTodoTitle: StateFlow<String> = _newTodoTitle.asStateFlow()
    
    private val _newTodoPriority = MutableStateFlow(Priority.MEDIUM)
    val newTodoPriority: StateFlow<Priority> = _newTodoPriority.asStateFlow()
    
    init {
        loadTodos()
        loadActiveTodoCount()
    }
    
    private fun loadTodos() {
        viewModelScope.launch {
            repository.getAllTodos().collect { todos ->
                _todoList.value = todos
            }
        }
    }
    
    private fun loadActiveTodoCount() {
        viewModelScope.launch {
            repository.getActiveTodoCount().collect { count ->
                _activeTodoCount.value = count
            }
        }
    }
    
    fun getFilteredTodos(): List<TodoItem> {
        return when (_filterType.value) {
            FilterType.ALL -> _todoList.value
            FilterType.ACTIVE -> _todoList.value.filter { !it.isCompleted }
            FilterType.COMPLETED -> _todoList.value.filter { it.isCompleted }
        }
    }
    
    fun setFilterType(type: FilterType) {
        _filterType.value = type
    }
    
    fun setNewTodoTitle(title: String) {
        _newTodoTitle.value = title
    }
    
    fun setNewTodoPriority(priority: Priority) {
        _newTodoPriority.value = priority
    }
    
    fun addTodo(title: String, priority: Priority = Priority.MEDIUM) {
        if (title.isBlank()) return
        
        val newTodo = TodoItem(
            title = title.trim(),
            priority = priority
        )
        
        viewModelScope.launch {
            repository.insertTodo(newTodo)
            _newTodoTitle.value = ""
            _newTodoPriority.value = Priority.MEDIUM
        }
    }
    
    fun updateTodo(todo: TodoItem) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }
    
    fun deleteTodo(todo: TodoItem) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }
    
    fun toggleTodoCompletion(todo: TodoItem) {
        viewModelScope.launch {
            repository.updateTodoCompletion(todo.id, !todo.isCompleted)
        }
    }
    
    fun deleteCompletedTodos() {
        viewModelScope.launch {
            repository.deleteCompletedTodos()
        }
    }
}

enum class FilterType {
    ALL, ACTIVE, COMPLETED
}