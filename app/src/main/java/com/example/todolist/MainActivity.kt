package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.TaskRepository.Singleton.tasksList
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val repo = TaskRepository()

        repo.loadData {
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

            taskAdapter = TaskAdapter(tasksList)

            recyclerView.adapter = taskAdapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }


        val editText = findViewById<EditText>(R.id.editText)
        findViewById<Button>(R.id.addTask).setOnClickListener {
            val taskTitle = editText.text.toString()
            if(taskTitle.isNotEmpty()){
                val task = Task(UUID.randomUUID().toString(),taskTitle, false)
                repo.addTask(task)
                editText.text.clear()
            }
        }


        findViewById<Button>(R.id.deleteDoneTask).setOnClickListener {
            val list = ArrayList(tasksList.filter { it.isItDone })
            repo.deleteTask(list)
        }

    }
}