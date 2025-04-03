package com.example.schooldiary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.schooldiary.data.models.viewmodels.SubjectViewModel
import com.example.schooldiary.data.models.viewmodels.SubjectViewModelFactory
import com.example.schooldiary.repository.SubjectRepository
import com.example.schooldiary.data.database.AppDatabase

class SubjectListFragment : Fragment() {

    private val subjectViewModel: SubjectViewModel by viewModels {
        SubjectViewModelFactory(SubjectRepository(AppDatabase.getDatabase(requireContext()).subjectDao(), requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SubjectListScreen(navController = findNavController(), viewModel = subjectViewModel)
            }
        }
    }
}