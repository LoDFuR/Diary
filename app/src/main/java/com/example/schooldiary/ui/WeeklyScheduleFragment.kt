package com.example.schooldiary.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schooldiary.R
import com.example.schooldiary.SchoolDiaryApplication
import com.example.schooldiary.databinding.FragmentWeeklyScheduleBinding
import com.example.schooldiary.viewmodel.ScheduleViewModel
import com.example.schooldiary.viewmodel.ScheduleViewModelFactory
import kotlinx.coroutines.launch

class WeeklyScheduleFragment : Fragment() {
    private var _binding: FragmentWeeklyScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ScheduleViewModel
    private val currentQuarter = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeeklyScheduleBinding.inflate(inflater, container, false)
        val appDatabase = (requireContext().applicationContext as SchoolDiaryApplication).database
        viewModel = ViewModelProvider(
            this,
            ScheduleViewModelFactory(appDatabase.subjectDao(), appDatabase.lessonDao())
        )[ScheduleViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WeekScheduleAdapter { day ->
            findNavController().navigate(
                R.id.action_to_daily,
                bundleOf("dayOfWeek" to day)
            )
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        val days = listOf(
            DayItem(1, getString(R.string.monday)),
            DayItem(2, getString(R.string.tuesday)),
            DayItem(3, getString(R.string.wednesday)),
            DayItem(4, getString(R.string.thursday)),
            DayItem(5, getString(R.string.friday)),
            DayItem(6, getString(R.string.saturday)),
            DayItem(7, getString(R.string.sunday))
        )
        adapter.submitList(days)

        binding.addSubjectButton.setOnClickListener {
            showAddSubjectDialog()
        }

        binding.helpButton.setOnClickListener {
            findNavController().navigate(R.id.action_to_help)
        }

        binding.viewSubjectsButton.setOnClickListener {
            findNavController().navigate(R.id.action_to_subjects)
        }

        viewModel.loadSchedule(currentQuarter)
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
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.addSubject(name, currentQuarter)
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