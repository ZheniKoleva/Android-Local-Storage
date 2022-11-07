package com.example.android_local_storage

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://restcountries.com/v2/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object CountryApi {
    val retrofitService: CountryService by lazy {
        retrofit.create(CountryService::class.java)
    }
}