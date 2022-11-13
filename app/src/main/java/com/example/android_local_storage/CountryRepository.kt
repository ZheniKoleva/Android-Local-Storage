package com.example.android_local_storage

import retrofit2.Call

class CountryRepository {
    fun getCountries(): Call<List<NetworkCountry>>? {
        return try {
            CountryApi.retrofitService.getAllCountries()
        } catch (e: Exception) {
            null
        }
    }
}