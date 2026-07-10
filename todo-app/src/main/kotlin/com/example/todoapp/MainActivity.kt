package com.example.todoapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.TodoDatabase
import com.example.todoapp.data.TodoRepository
import com.example.todoapp.ui.screen.TodoListScreen
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.viewmodel.TodoViewModel
import com.example.todoapp.viewmodel.TodoViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = TodoDatabase.getDatabase(this)
        val repository = TodoRepository(database.todoDao())
        val viewModel = ViewModelProvider(
            this,
            TodoViewModelFactory(repository)
        ).get(TodoViewModel::class.java)
        
        setContent {
            TodoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoListScreen(viewModel)
                }
            }
        }
    }
}

class TodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}