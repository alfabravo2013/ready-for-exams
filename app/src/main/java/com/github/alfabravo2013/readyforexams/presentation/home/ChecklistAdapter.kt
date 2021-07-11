package com.github.alfabravo2013.readyforexams.presentation.home

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.alfabravo2013.readyforexams.R
import com.github.alfabravo2013.readyforexams.databinding.ItemHomeChecklistBinding
import com.github.alfabravo2013.readyforexams.domain.models.Checklist

class ChecklistAdapter : RecyclerView.Adapter<ChecklistAdapter.ViewHolder>() {
    private val checklists = mutableListOf<Checklist>()

    class ViewHolder(
        private val binding: ItemHomeChecklistBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private var statusColor = 0
        private lateinit var statusText: String
        private lateinit var tasksCountText: String

        fun bind(item: Checklist) {
            with(binding) {
                setVariables(item.completed, item.total)

                homeChecklistItemName.text = item.name
                homeChecklistItemTasksCount.text = tasksCountText
                homeChecklistItemStatusText.text = statusText

                ImageViewCompat.setImageTintList(
                    homeChecklistItemStatusIcon,
                    ColorStateList.valueOf(statusColor)
                )

                homeChecklistItemStatusText.setTextColor(statusColor)
            }
        }

        private fun setVariables(completed: Int, total: Int) {
            val context = binding.root.context

            tasksCountText =
                context.getString(R.string.home_checklist_tasks_count_text, completed, total)

            when (completed) {
                0 -> {
                    statusText = context.getString(R.string.home_checklist_not_started_text)
                    statusColor = ContextCompat.getColor(context, R.color.red_brick)
                }
                total -> {
                    statusText = context.getString(R.string.home_checklist_in_progress_text)
                    statusColor = ContextCompat.getColor(context, R.color.green)
                }
                else -> {
                    statusText = context.getString(R.string.home_checklist_done_text)
                    statusColor = ContextCompat.getColor(context, R.color.font_gray)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeChecklistBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val item = checklists[position]
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int = checklists.size

    fun setItems(items: List<Checklist>) {
        checklists.clear()
        checklists.addAll(items)
        notifyDataSetChanged()
    }
}
