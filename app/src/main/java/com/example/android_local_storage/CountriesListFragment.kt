package com.example.android_local_storage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_local_storage.databinding.FragmentCountriesListBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesListFragment : Fragment() {
    private lateinit var binding: FragmentCountriesListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountriesListBinding.inflate(inflater, container, false)

        /*val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val countryService = retrofit.create(CountryService::class.java)
        val countryRepository = CountryRepository(countryService)  */
        val countryRepository = CountryRepository()

        countryRepository.getCountries()?.enqueue(object : Callback<List<Country>> {
            override fun onResponse(
                call: Call<List<Country>>, response: Response<List<Country>>
            ) {
                val countries = response.body() ?: return
                val adapter = CountryAdapter(countries)
                binding.countriesList.adapter = adapter
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Snackbar.make(binding.root, "Failure to load countries", Snackbar.LENGTH_LONG)
                    .show()
            }
        })

        return binding.root
    }
}