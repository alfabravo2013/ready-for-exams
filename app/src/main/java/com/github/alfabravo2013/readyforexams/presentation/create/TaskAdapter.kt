package com.github.alfabravo2013.readyforexams.presentation.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.alfabravo2013.readyforexams.databinding.ItemCreateTaskBinding

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private val taskList = mutableListOf<String>()

    class ViewHolder(
        private val binding: ItemCreateTaskBinding
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.createItemNameText.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCreateTaskBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val item = taskList[position]
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int = taskList.size

    fun addItem(item: String) {
        taskList.add(item)
        notifyItemInserted(taskList.lastIndex)
    }
}