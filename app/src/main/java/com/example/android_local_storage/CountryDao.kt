package com.example.android_local_storage

import androidx.room.*

@Dao
interface CountryDao {
    @Query("SELECT * FROM Country")
    suspend fun getAll(): List<Country>

    @Query("SELECT * FROM Country WHERE alpha2Code=:id")
    suspend fun getCountryById(id: String): Country

    @Query("SELECT Count(*) from Country")
    suspend fun getCountriesCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg country: Country)

    @Delete
    suspend fun delete(country: Country)

    @Update
    suspend fun updateAll(vararg countries: Country)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncDate(vararg date: LastSync)

    @Query("SELECT * FROM LastSync ORDER BY updateDate DESC LIMIT 1")
    suspend fun getLastUpdateDate(): LastSync?
}