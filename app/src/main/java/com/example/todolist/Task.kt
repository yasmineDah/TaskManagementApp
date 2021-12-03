package com.example.todolist

data class Task (
    val id : String = "task1",
    val title : String = "task",
    var isItDone : Boolean = false
    )