package com.example.schooldiary.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schooldiary.R
import com.example.schooldiary.SchoolDiaryApplication
import com.example.schooldiary.data.database.Lesson
import com.example.schooldiary.databinding.FragmentDailyDetailsBinding
import com.example.schooldiary.viewmodel.ScheduleViewModel
import com.example.schooldiary.viewmodel.ScheduleViewModelFactory
import kotlinx.coroutines.launch

class DailyDetailsFragment : Fragment() {
    private var _binding: FragmentDailyDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyDetailsBinding.inflate(inflater, container, false)
        val appDatabase = (requireContext().applicationContext as SchoolDiaryApplication).database
        viewModel = ViewModelProvider(
            this,
            ScheduleViewModelFactory(appDatabase.subjectDao(), appDatabase.lessonDao())
        )[ScheduleViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayOfWeek = arguments?.getInt("dayOfWeek") ?: 1
        val appDatabase = (requireContext().applicationContext as SchoolDiaryApplication).database
        val adapter = LessonAdapter(
            subjectDao = appDatabase.subjectDao(),
            onEditLesson = { lesson -> showEditLessonDialog(lesson) },
            onDeleteLesson = { lesson ->
                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        viewModel.deleteLesson(lesson)
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "Ошибка удаления урока: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        viewModel.dailyLessons.observe(viewLifecycleOwner) { lessons ->
            adapter.submitList(lessons)
            binding.emptyText.visibility = if (lessons.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.subjectsWithGrades.observe(viewLifecycleOwner) { subjectsWithGrades ->
            val text = if (subjectsWithGrades.isEmpty()) {
                getString(R.string.avg_gr)
            } else {
                subjectsWithGrades.joinToString("\n") {
                    "${it.subject.name} (ID: ${it.subject.id}): ${if (it.averageGrade == 0f) getString(R.string.no_grade) else String.format("%.2f", it.averageGrade)}"
                }
            }
            binding.averageGradesText.text = getString(R.string.avg_gr_is) + ":\n$text"
        }

        binding.addLessonButton.setOnClickListener {
            showAddLessonDialog(dayOfWeek)
        }

        viewModel.loadDayLessons(dayOfWeek)
        viewModel.loadSubjectsWithAverageGrades()
    }

    private fun showAddLessonDialog(dayOfWeek: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_lesson, null)
        val subjectIdEditText = dialogView.findViewById<EditText>(R.id.subjectIdEditText)
        val homeworkEditText = dialogView.findViewById<EditText>(R.id.homeworkEditText)
        val gradeEditText = dialogView.findViewById<EditText>(R.id.gradeEditText)

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.add_subject))
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val subjectId = subjectIdEditText.text.toString().toLongOrNull()
                val homework = homeworkEditText.text.toString().takeIf { it.isNotEmpty() }
                val grade = gradeEditText.text.toString().toIntOrNull()
                if (subjectId != null) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        val subjectExists = viewModel.subjectExists(subjectId)
                        if (subjectExists) {
                            viewModel.addLesson(subjectId, dayOfWeek, homework, grade)
                            viewModel.loadSubjectsWithAverageGrades()
                        } else {
                            Toast.makeText(
                                context,
                                "Предмет с ID $subjectId не существует",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Введите действительный ID предмета", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun showEditLessonDialog(lesson: Lesson) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_lesson, null)
        val subjectIdEditText = dialogView.findViewById<EditText>(R.id.subjectIdEditText)
        val homeworkEditText = dialogView.findViewById<EditText>(R.id.homeworkEditText)
        val gradeEditText = dialogView.findViewById<EditText>(R.id.gradeEditText)

        subjectIdEditText.setText(lesson.subjectId.toString())
        homeworkEditText.setText(lesson.homework ?: "")
        gradeEditText.setText(lesson.grade?.toString() ?: "")

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.redact_les))
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val subjectId = subjectIdEditText.text.toString().toLongOrNull()
                val homework = homeworkEditText.text.toString().takeIf { it.isNotEmpty() }
                val grade = gradeEditText.text.toString().toIntOrNull()
                if (subjectId != null) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        val subjectExists = viewModel.subjectExists(subjectId)
                        if (subjectExists) {
                            viewModel.updateLesson(
                                lesson.copy(
                                    subjectId = subjectId,
                                    homework = homework,
                                    grade = grade
                                )
                            )
                            viewModel.loadSubjectsWithAverageGrades()
                        } else {
                            Toast.makeText(
                                context,
                                "Предмет с ID $subjectId не существует",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Введите действительный ID предмета", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}