package com.example.schooldiary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schooldiary.SchoolDiaryApplication
import com.example.schooldiary.databinding.FragmentSubjectsBinding
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.subjectsWithGrades.collect { subjectsWithGrades ->
                adapter.submitList(subjectsWithGrades)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}