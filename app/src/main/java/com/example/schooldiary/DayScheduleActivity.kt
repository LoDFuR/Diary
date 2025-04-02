package com.example.schooldiary;
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import com.example.schooldiary.databinding.ActivityDayScheduleBinding

class DayScheduleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDayScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve and display the schedule for the selected day
    }
}