package com.example.schooldiary.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.schooldiary.R
import com.example.schooldiary.data.database.Subject

data class DayItem(val dayOfWeek: Int, val name: String)

class WeekScheduleAdapter(
    private val onDayClick: (Int) -> Unit
) : ListAdapter<DayItem, RecyclerView.ViewHolder>(DiffCallback()) {

    private var subjects: List<Subject> = emptyList()

    fun updateSubjects(newSubjects: List<Subject>) {
        subjects = newSubjects
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < currentList.size) {
            VIEW_TYPE_DAY
        } else {
            VIEW_TYPE_SUBJECT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DAY -> DayViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
            )
            VIEW_TYPE_SUBJECT -> SubjectViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_subject_schedule, parent, false)
            )
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DayViewHolder -> holder.bind(currentList[position])
            is SubjectViewHolder -> holder.bind(subjects[position - currentList.size])
        }
    }

    override fun getItemCount(): Int = currentList.size + subjects.size

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayName: TextView = itemView.findViewById(R.id.dayName)
        fun bind(day: DayItem) {
            dayName.text = day.name
            itemView.setOnClickListener { onDayClick(day.dayOfWeek) }
        }
    }

    inner class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val subjectName: TextView = itemView.findViewById(R.id.subjectName)

        private val editButton: Button = itemView.findViewById(R.id.editButton)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        fun bind(subject: Subject) {
            subjectName.text = subject.name
            editButton.setOnClickListener { onEditSubject?.invoke(subject) }
            deleteButton.setOnClickListener { onDeleteSubject?.invoke(subject) }
        }
    }

    var onEditSubject: ((Subject) -> Unit)? = null
    var onDeleteSubject: ((Subject) -> Unit)? = null

    companion object {
        private const val VIEW_TYPE_DAY = 0
        private const val VIEW_TYPE_SUBJECT = 1
    }

    private class DiffCallback : DiffUtil.ItemCallback<DayItem>() {
        override fun areItemsTheSame(oldItem: DayItem, newItem: DayItem): Boolean {
            return oldItem.dayOfWeek == newItem.dayOfWeek
        }

        override fun areContentsTheSame(oldItem: DayItem, newItem: DayItem): Boolean {
            return oldItem == newItem
        }
    }
}