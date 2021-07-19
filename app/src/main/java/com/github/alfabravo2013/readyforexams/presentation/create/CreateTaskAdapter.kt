package com.github.alfabravo2013.readyforexams.presentation.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.alfabravo2013.readyforexams.databinding.ItemCreateTaskBinding
import com.github.alfabravo2013.readyforexams.presentation.models.TaskRepresentation

class CreateTaskAdapter : RecyclerView.Adapter<CreateTaskAdapter.ViewHolder>() {
    private val tasks = mutableListOf<TaskRepresentation>()

    class ViewHolder(
        private val binding: ItemCreateTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TaskRepresentation) {
            binding.createItemNameText.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCreateTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tasks[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = tasks.size

    fun setItems(items: List<TaskRepresentation>) {
        tasks.clear()
        tasks.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: TaskRepresentation) {
        tasks.add(item)
        notifyItemInserted(tasks.lastIndex)
    }
}
