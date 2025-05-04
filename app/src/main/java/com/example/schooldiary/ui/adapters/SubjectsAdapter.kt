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
import com.example.schooldiary.data.database.Grade
import com.example.schooldiary.data.database.Subject
import com.example.schooldiary.viewmodel.SubjectWithGrades
import androidx.fragment.app.Fragment
import com.example.schooldiary.R.string.no_grade

class SubjectsAdapter : ListAdapter<SubjectWithGrades, SubjectsAdapter.SubjectViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SubjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val subjectName: TextView = itemView.findViewById(R.id.subjectName)
        private val gradesText: TextView = itemView.findViewById(R.id.gradesText)
        private val editSubjectButton: Button = itemView.findViewById(R.id.editSubjectButton)
        private val deleteSubjectButton: Button = itemView.findViewById(R.id.deleteSubjectButton)
        private val addGradeButton: Button = itemView.findViewById(R.id.addGradeButton)

        fun bind(item: SubjectWithGrades) {
            subjectName.text = item.subject.name
            gradesText.text = if (item.grades.isEmpty()) ({
                no_grade
            }).toString() else {
                item.grades.joinToString(", ") { "${it.value} (${it.date})" }
            }
            editSubjectButton.setOnClickListener { onEditSubject?.invoke(item.subject) }
            deleteSubjectButton.setOnClickListener { onDeleteSubject?.invoke(item.subject) }
            addGradeButton.setOnClickListener { onAddGrade?.invoke(item.subject.id) }
        }
    }

    var onEditSubject: ((Subject) -> Unit)? = null
    var onDeleteSubject: ((Subject) -> Unit)? = null
    var onAddGrade: ((Long) -> Unit)? = null
    var onEditGrade: ((Grade) -> Unit)? = null
    var onDeleteGrade: ((Grade) -> Unit)? = null

    private class DiffCallback : DiffUtil.ItemCallback<SubjectWithGrades>() {
        override fun areItemsTheSame(oldItem: SubjectWithGrades, newItem: SubjectWithGrades): Boolean {
            return oldItem.subject.id == newItem.subject.id
        }

        override fun areContentsTheSame(oldItem: SubjectWithGrades, newItem: SubjectWithGrades): Boolean {
            return oldItem == newItem
        }
    }
}