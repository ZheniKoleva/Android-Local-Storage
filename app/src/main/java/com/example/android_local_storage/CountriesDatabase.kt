package com.example.android_local_storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Country::class, LastSync::class], version = 1)
abstract class CountriesDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao
}
