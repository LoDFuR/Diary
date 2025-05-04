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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schooldiary.R
import com.example.schooldiary.SchoolDiaryApplication
import com.example.schooldiary.data.database.Subject
import com.example.schooldiary.databinding.FragmentSubjectsBinding
import com.example.schooldiary.ui.adapters.SubjectsAdapter
import com.example.schooldiary.viewmodel.SubjectsViewModel
import com.example.schooldiary.viewmodel.SubjectsViewModelFactory
import kotlinx.coroutines.launch

class SubjectsFragment : Fragment() {
    private var _binding: FragmentSubjectsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SubjectsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubjectsBinding.inflate(inflater, container, false)
        val appDatabase = (requireContext().applicationContext as SchoolDiaryApplication).database
        viewModel = ViewModelProvider(
            this,
            SubjectsViewModelFactory(appDatabase.subjectDao(), appDatabase.gradeDao())
        )[SubjectsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SubjectsAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        adapter.onEditSubject = { subject ->
            showEditSubjectDialog(subject)
        }
        adapter.onDeleteSubject = { subject ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.deleteSubject(subject)
            }
        }
        adapter.onAddGrade = { subjectId ->
            showAddGradeDialog(subjectId)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.subjectsWithGrades.collect { subjectsWithGrades ->
                adapter.submitList(subjectsWithGrades)
            }
        }
    }

    private fun showEditSubjectDialog(subject: Subject) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_subject, null)
        val editText = dialogView.findViewById<EditText>(R.id.subjectNameEditText)
        editText.setText(subject.name)

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.mod_sub))
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val name = editText.text.toString()
                if (name.isNotEmpty()) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.updateSubject(subject.copy(name = name))
                    }
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun showAddGradeDialog(subjectId: Long) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_grade, null)
        val gradeEditText = dialogView.findViewById<EditText>(R.id.gradeValueEditText)
        val dateEditText = dialogView.findViewById<EditText>(R.id.gradeDateEditText)

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.add_grade))
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val value = gradeEditText.text.toString().toIntOrNull()
                val date = dateEditText.text.toString()
                if (value != null && date.isNotEmpty()) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.addGrade(subjectId, value, date)
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