package com.example.schooldiary.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.schooldiary.databinding.ItemDayBinding

data class DayItem(val dayOfWeek: Int, val name: String)

class WeekScheduleAdapter(
    private val onDayClick: (Int) -> Unit
) : ListAdapter<DayItem, WeekScheduleAdapter.DayViewHolder>(DayDiffCallback()) {

    class DayViewHolder(
        private val binding: ItemDayBinding,
        private val onDayClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(day: DayItem) {
            binding.dayName.text = day.name
            binding.root.setOnClickListener {
                onDayClick(day.dayOfWeek)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding = ItemDayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DayViewHolder(binding, onDayClick)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DayDiffCallback : DiffUtil.ItemCallback<DayItem>() {
    override fun areItemsTheSame(oldItem: DayItem, newItem: DayItem): Boolean =
        oldItem.dayOfWeek == newItem.dayOfWeek

    override fun areContentsTheSame(oldItem: DayItem, newItem: DayItem): Boolean =
        oldItem == newItem
}