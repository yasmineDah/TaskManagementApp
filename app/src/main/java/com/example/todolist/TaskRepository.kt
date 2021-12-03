package com.example.todolist

import com.example.todolist.TaskRepository.Singleton.databaseRef
import com.example.todolist.TaskRepository.Singleton.tasksList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TaskRepository {
    object Singleton{
        val databaseRef = FirebaseDatabase.getInstance().getReference("tasks")
        val tasksList = mutableListOf<Task>()
    }

    fun loadData(callback : () -> Unit){
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                tasksList.clear()
                for(ds in snapshot.children){
                    val task = ds.getValue(Task::class.java)
                    if(task != null)
                        tasksList.add(task)
                }
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun deleteTask(tasks : ArrayList<Task>) {
        for(task in tasks)
            databaseRef.child(task.id).removeValue()
    }

    fun addTask(task : Task) = databaseRef.child(task.id).setValue(task)
}