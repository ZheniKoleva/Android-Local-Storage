package com.example.android_local_storage

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.room.Room
import com.example.android_local_storage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var db: CountriesDatabase
    lateinit var loadingSpinner: ProgressBar
    lateinit var mainContainer: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        db = Room.databaseBuilder(
            applicationContext,
            CountriesDatabase::class.java,
            "country_database"
        ).build()

        loadingSpinner = binding.loadingSpinner
        mainContainer = binding.mainActivityContainerCl

        setContentView(binding.root)
    }
}