package com.example.todoapp.ui.screen

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.Priority
import com.example.todoapp.data.TodoItem
import com.example.todoapp.viewmodel.FilterType
import com.example.todoapp.viewmodel.TodoViewModel

@Composable
fun TodoListScreen(viewModel: TodoViewModel) {
    val todoList by viewModel.todoList.collectAsState()
    val filterType by viewModel.filterType.collectAsState()
    val activeTodoCount by viewModel.activeTodoCount.collectAsState()
    val newTodoTitle by viewModel.newTodoTitle.collectAsState()
    val newTodoPriority by viewModel.newTodoPriority.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    
    val filteredTodos = viewModel.getFilteredTodos()
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Todo")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            TodoHeader(activeTodoCount)
            FilterTabs(filterType) { newFilter ->
                viewModel.setFilterType(newFilter)
            }
            
            if (filteredTodos.isEmpty()) {
                EmptyState(filterType)
            } else {
                TodoList(
                    todos = filteredTodos,
                    onToggle = { viewModel.toggleTodoCompletion(it) },
                    onDelete = { viewModel.deleteTodo(it) },
                    onUpdate = { viewModel.updateTodo(it) }
                )
            }
        }
    }
    
    if (showAddDialog) {
        AddTodoDialog(
            title = newTodoTitle,
            priority = newTodoPriority,
            onTitleChange = { viewModel.setNewTodoTitle(it) },
            onPriorityChange = { viewModel.setNewTodoPriority(it) },
            onAdd = {
                viewModel.addTodo(newTodoTitle, newTodoPriority)
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    }
}

@Composable
fun TodoHeader(activeTodoCount: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Text(
            "My Tasks",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )
        Text(
            "$activeTodoCount active",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun FilterTabs(currentFilter: FilterType, onFilterChange: (FilterType) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FilterType.values().forEach { filter ->
            FilterTab(
                label = filter.name,
                isSelected = currentFilter == filter,
                onClick = { onFilterChange(filter) }
            )
        }
    }
}

@Composable
fun FilterTab(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(4.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (isSelected) Color.White else Color.Black,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun TodoList(
    todos: List<TodoItem>,
    onToggle: (TodoItem) -> Unit,
    onDelete: (TodoItem) -> Unit,
    onUpdate: (TodoItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(todos, key = { it.id }) { todo ->
            TodoItemCard(
                todo = todo,
                onToggle = { onToggle(todo) },
                onDelete = { onDelete(todo) }
            )
        }
    }
}

@Composable
fun TodoItemCard(
    todo: TodoItem,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (todo.priority) {
                Priority.LOW -> MaterialTheme.colorScheme.surface
                Priority.MEDIUM -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                Priority.HIGH -> Color(0xFFFFE082).copy(alpha = 0.2f)
                Priority.URGENT -> Color(0xFFEF5350).copy(alpha = 0.2f)
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onToggle() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = todo.isCompleted,
                        onCheckedChange = { onToggle() }
                    )
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            todo.title,
                            style = MaterialTheme.typography.bodyLarge,
                            textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        
                        PriorityBadge(todo.priority)
                    }
                }
                
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun PriorityBadge(priority: Priority) {
    val (backgroundColor, textColor) = when (priority) {
        Priority.LOW -> Color(0xFF4CAF50) to Color.White
        Priority.MEDIUM -> Color(0xFF2196F3) to Color.White
        Priority.HIGH -> Color(0xFFFFC107) to Color.Black
        Priority.URGENT -> Color(0xFFF44336) to Color.White
    }
    
    Surface(
        modifier = Modifier.padding(top = 4.dp),
        color = backgroundColor,
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            priority.name,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun EmptyState(filterType: FilterType) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Filled.CheckCircle,
            contentDescription = "Empty",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Text(
            when (filterType) {
                FilterType.ALL -> "No tasks yet"
                FilterType.ACTIVE -> "All done!"
                FilterType.COMPLETED -> "No completed"
            },
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun AddTodoDialog(
    title: String,
    priority: Priority,
    onTitleChange: (String) -> Unit,
    onPriorityChange: (Priority) -> Unit,
    onAdd: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Task") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = onTitleChange,
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                
                Text("Priority:", style = MaterialTheme.typography.labelMedium)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Priority.values().forEach { p ->
                        FilterChip(
                            selected = priority == p,
                            onClick = { onPriorityChange(p) },
                            label = { Text(p.name) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onAdd, enabled = title.isNotBlank()) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}