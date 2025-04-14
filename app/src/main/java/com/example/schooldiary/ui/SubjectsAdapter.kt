package com.example.schooldiary.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.schooldiary.databinding.ItemSubjectBinding
import com.example.schooldiary.viewmodel.SubjectWithGrades

class SubjectsAdapter : ListAdapter<SubjectWithGrades, SubjectsAdapter.SubjectViewHolder>(SubjectDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = ItemSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SubjectViewHolder(private val binding: ItemSubjectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SubjectWithGrades) {
            binding.subjectName.text = item.subject.name
            binding.grades.text = if (item.grades.isEmpty()) {
                "Нет оценок"
            } else {
                item.grades.joinToString(", ") { "${it.value} (${it.date})" }
            }
        }
    }

    class SubjectDiffCallback : DiffUtil.ItemCallback<SubjectWithGrades>() {
        override fun areItemsTheSame(oldItem: SubjectWithGrades, newItem: SubjectWithGrades): Boolean {
            return oldItem.subject.id == newItem.subject.id
        }

        override fun areContentsTheSame(oldItem: SubjectWithGrades, newItem: SubjectWithGrades): Boolean {
            return oldItem == newItem
        }
    }
}