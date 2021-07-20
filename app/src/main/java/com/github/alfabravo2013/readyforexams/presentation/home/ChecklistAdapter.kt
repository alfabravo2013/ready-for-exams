package com.github.alfabravo2013.readyforexams.presentation.home

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.alfabravo2013.readyforexams.databinding.ItemHomeChecklistBinding
import com.github.alfabravo2013.readyforexams.presentation.models.ChecklistRepresentation

class ChecklistAdapter : RecyclerView.Adapter<ChecklistAdapter.ViewHolder>() {
    private val checklists = mutableListOf<ChecklistRepresentation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeChecklistBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = checklists[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = checklists.size

    fun setItems(items: List<ChecklistRepresentation>) {
        checklists.clear()
        checklists.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemHomeChecklistBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChecklistRepresentation) {
            val context = binding.root.context
            val color = ContextCompat.getColor(context, item.statusColorResource)

            with(binding) {
                homeChecklistItemName.text = item.name

                homeChecklistItemTasksCount.text = context.getString(
                    item.taskCountResource,
                    item.completed,
                    item.total
                )

                homeChecklistItemStatusText.text = context.getString(item.statusTextResource)

                ImageViewCompat.setImageTintList(
                    homeChecklistItemStatusIcon,
                    ColorStateList.valueOf(color)
                )

                homeChecklistItemStatusText.setTextColor(color)
            }
        }
    }
}
