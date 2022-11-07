package com.example.android_local_storage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.android_local_storage.databinding.FragmentCountryDetailsBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCountryDetailsBinding
    private lateinit var args: CountryDetailsFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryDetailsBinding.inflate(inflater, container, false)
        args = navArgs<CountryDetailsFragmentArgs>().value

        /*val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val countryService = retrofit.create(CountryService::class.java)
        val countryRepository = CountryRepository(countryService)  */
       val countryRepository = CountryRepository()

        countryRepository.getCountryDetails(args.countryName)
            ?.enqueue(object : Callback<List<NetworkCountryDetails>> {
                override fun onResponse(
                    call: Call<List<NetworkCountryDetails>>, response: Response<List<NetworkCountryDetails>>
                ) {
                    val country = response.body()?.get(0) ?: return
                    val viewCountry = context?.let { country.asCountryDetails(it) }
                    binding.apply {
                        this.country = viewCountry

                        Glide.with(root.context)
                            .load(country.flags.png)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(ivFlag)
                    }
                }

                override fun onFailure(call: Call<List<NetworkCountryDetails>>, t: Throwable) {
                    Snackbar.make(binding.root, "Failure to load countries", Snackbar.LENGTH_LONG)
                        .show()
                }
            })

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}