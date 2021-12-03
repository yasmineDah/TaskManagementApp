package com.example.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TaskAdapter(
    private val tasks : MutableList<Task>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    class TaskViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.taskTitle)
        val taskIfDone = itemView.findViewById<CheckBox>(R.id.checkedIfDone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.task,
                parent,
                false
            )
        )
    }

    fun addTask(task : Task){
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    }

    fun deleteTaskIfDone(){
        tasks.removeAll { task -> task.isItDone }
        notifyDataSetChanged()
    }


    private fun strikeThrough (taskTitle: TextView, isCheckBox: Boolean){
       if(isCheckBox){
           taskTitle.paintFlags = taskTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
       }else{
           taskTitle.paintFlags = taskTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
       }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]

        holder.title.text = currentTask.title
        holder.taskIfDone.isChecked = currentTask.isItDone
        strikeThrough(holder.title,currentTask.isItDone)
        holder.taskIfDone.setOnCheckedChangeListener { _, isChecked ->
            strikeThrough(holder.title,isChecked)
            currentTask.isItDone = !currentTask.isItDone
            TaskRepository().addTask(currentTask)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}