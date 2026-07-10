package com.example.todoapp.data

import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {
    
    fun getAllTodos(): Flow<List<TodoItem>> = todoDao.getAllTodos()
    
    fun getActiveTodos(): Flow<List<TodoItem>> = todoDao.getActiveTodos()
    
    fun getCompletedTodos(): Flow<List<TodoItem>> = todoDao.getCompletedTodos()
    
    fun getActiveTodoCount(): Flow<Int> = todoDao.getActiveTodoCount()
    
    suspend fun insertTodo(todo: TodoItem): Long = todoDao.insertTodo(todo)
    
    suspend fun updateTodo(todo: TodoItem) = todoDao.updateTodo(todo)
    
    suspend fun deleteTodo(todo: TodoItem) = todoDao.deleteTodo(todo)
    
    suspend fun deleteTodoById(id: Int) = todoDao.deleteTodoById(id)
    
    suspend fun updateTodoCompletion(id: Int, completed: Boolean) =
        todoDao.updateTodoCompletion(id, completed)
    
    suspend fun deleteCompletedTodos() = todoDao.deleteCompletedTodos()
    
    suspend fun getTodoById(id: Int): TodoItem? = todoDao.getTodoById(id)
}