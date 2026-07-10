package com.example.todoapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY priority DESC, dueDate ASC")
    fun getAllTodos(): Flow<List<TodoItem>>
    
    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: Int): TodoItem?
    
    @Query("SELECT * FROM todos WHERE isCompleted = 0 ORDER BY priority DESC, dueDate ASC")
    fun getActiveTodos(): Flow<List<TodoItem>>
    
    @Query("SELECT * FROM todos WHERE isCompleted = 1 ORDER BY updatedAt DESC")
    fun getCompletedTodos(): Flow<List<TodoItem>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoItem): Long
    
    @Update
    suspend fun updateTodo(todo: TodoItem)
    
    @Delete
    suspend fun deleteTodo(todo: TodoItem)
    
    @Query("DELETE FROM todos WHERE id = :id")
    suspend fun deleteTodoById(id: Int)
    
    @Query("UPDATE todos SET isCompleted = :completed WHERE id = :id")
    suspend fun updateTodoCompletion(id: Int, completed: Boolean)
    
    @Query("DELETE FROM todos WHERE isCompleted = 1")
    suspend fun deleteCompletedTodos()
    
    @Query("SELECT COUNT(*) FROM todos WHERE isCompleted = 0")
    fun getActiveTodoCount(): Flow<Int>
}