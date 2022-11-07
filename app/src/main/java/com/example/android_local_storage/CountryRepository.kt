package com.example.android_local_storage

import retrofit2.Call

class CountryRepository(
    //private val countryService: CountryService
) {
    fun getCountries(): Call<List<Country>>? {
        return try {
            //countryService.getAllCountries()
            CountryApi.retrofitService.getAllCountries()
        } catch (e: Exception) {
            null
        }
    }

    fun getCountryDetails(name: String): Call<List<NetworkCountryDetails>>? {
        return try {
            //countryService.getCountryDetails(name)
            CountryApi.retrofitService.getCountryDetails(name)
        } catch (e: Exception) {
            null
        }
    }
}