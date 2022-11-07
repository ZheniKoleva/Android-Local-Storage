package com.example.android_local_storage

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryService {
    @GET("all")
    fun getAllCountries(): Call<List<Country>>

    @GET("name/{countryName}")
    fun getCountryDetails(@Path("countryName") name: String): Call<List<NetworkCountryDetails>>
}