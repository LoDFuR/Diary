package com.example.schooldiary.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.schooldiary.R
import com.example.schooldiary.data.database.Lesson
import com.example.schooldiary.data.database.SubjectDao
import com.example.schooldiary.databinding.ItemLessonBinding
import kotlinx.coroutines.runBlocking

class LessonAdapter(
    private val subjectDao: SubjectDao,
    private val onEditLesson: (Lesson) -> Unit,
    private val onDeleteLesson: (Lesson) -> Unit
) : ListAdapter<Lesson, LessonAdapter.ViewHolder>(LessonDiffCallback()) {

    class ViewHolder(
        private val binding: ItemLessonBinding,
        private val subjectDao: SubjectDao,
        private val onEditLesson: (Lesson) -> Unit,
        private val onDeleteLesson: (Lesson) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lesson: Lesson) {
            val context = binding.root.context
            val subject = runBlocking {
                subjectDao.getSubjectById(lesson.subjectId)
            }
            binding.subjectText.text = context.getString(
                R.string.subject_label,
                "${subject?.name ?: context.getString(R.string.unknown_subject)} (ID: ${lesson.subjectId})"
            )
            binding.homeworkText.text = context.getString(
                R.string.homework_label,
                lesson.homework ?: context.getString(R.string.no_homework)
            )
            binding.gradeText.text = context.getString(
                R.string.grade_label,
                lesson.grade?.toString() ?: context.getString(R.string.no_grade)
            )
            binding.editLessonButton.setOnClickListener { onEditLesson(lesson) }
            binding.deleteLessonButton.setOnClickListener { onDeleteLesson(lesson) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLessonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, subjectDao, onEditLesson, onDeleteLesson)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class LessonDiffCallback : DiffUtil.ItemCallback<Lesson>() {
    override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean =
        oldItem == newItem
}