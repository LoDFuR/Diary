package com.example.schooldiary.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schooldiary.R
import com.example.schooldiary.SchoolDiaryApplication
import com.example.schooldiary.databinding.FragmentDayScheduleBinding
import com.example.schooldiary.viewmodel.ScheduleViewModel
import com.example.schooldiary.viewmodel.ScheduleViewModelFactory
import kotlinx.coroutines.launch

class DailyDetailsFragment : Fragment() {
    private var _binding: FragmentDayScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ScheduleViewModel
    private val dayOfWeek by lazy { arguments?.getInt("dayOfWeek") ?: 1 }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDayScheduleBinding.inflate(inflater, container, false)
        val appDatabase = (requireContext().applicationContext as SchoolDiaryApplication).database
        viewModel = ViewModelProvider(
            this,
            ScheduleViewModelFactory(appDatabase.subjectDao(), appDatabase.lessonDao())
        )[ScheduleViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lessonAdapter = LessonAdapter()
        binding.lessonList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lessonAdapter
        }

        viewModel.dailyLessons.observe(viewLifecycleOwner) { lessons ->
            lessonAdapter.submitList(lessons)
            val avg = lessons.firstOrNull()?.let { viewModel.calculateAverageGrade(it.subjectId) } ?: 0f
            binding.averageGradeText.text = String.format(getString(R.string.average_grade), avg)
        }

        binding.addLessonButton.setOnClickListener {
            showAddLessonDialog()
        }

        binding.addSubjectButton.setOnClickListener {
            showAddSubjectDialog()
        }

        binding.helpButton.setOnClickListener {
            findNavController().navigate(R.id.action_to_help)
        }

        viewModel.loadDayLessons(dayOfWeek)
        viewModel.loadSchedule(1) // Загружаем предметы для текущей четверти
    }

    private fun showAddLessonDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_lesson, null)
        val homeworkEdit = dialogView.findViewById<EditText>(R.id.homeworkEditText)
        val gradeEdit = dialogView.findViewById<EditText>(R.id.gradeEditText)
        val subjectIdEdit = dialogView.findViewById<EditText>(R.id.subjectIdEditText)

        AlertDialog.Builder(requireContext())
            .setTitle(R.string.daily_details)
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val homework = homeworkEdit.text.toString().ifEmpty { null }
                val grade = gradeEdit.text.toString().toIntOrNull()?.coerceIn(0, 5)
                val subjectId = subjectIdEdit.text.toString().toIntOrNull() ?: return@setPositiveButton
                lifecycleScope.launch {
                    viewModel.addLesson(subjectId.toLong(), dayOfWeek, homework, grade)
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun showAddSubjectDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_subject, null)
        val editText = dialogView.findViewById<EditText>(R.id.subjectNameEditText)

        AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_subject)
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val name = editText.text.toString()
                if (name.isNotEmpty()) {
                    lifecycleScope.launch {
                        viewModel.addSubject(name, 1)
                    }
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