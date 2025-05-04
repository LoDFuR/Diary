package com.example.schooldiary.ui.viewmodel




//package com.example.schooldiary.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schooldiary.data.database.Lesson
import com.example.schooldiary.databinding.ItemLessonBinding // Создайте этот layout

class DayScheduleAdapter(private val lessons: List<Lesson>) :
    RecyclerView.Adapter<DayScheduleAdapter.LessonViewHolder>() {

    class LessonViewHolder(private val binding: ItemLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(lesson: Lesson) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val binding = ItemLessonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LessonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(lessons[position])
    }

    override fun getItemCount(): Int = lessons.size
}