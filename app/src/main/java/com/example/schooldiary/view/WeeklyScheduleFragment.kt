package com.example.schedule.view

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import androidx.fragment.app.Fragment
import com.example.schooldiary.R

//import com.example.schooldiary.R

class WeeklyScheduleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weekly_schedule, container, false)
    }
}