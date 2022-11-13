package com.example.android_local_storage

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.android_local_storage.databinding.FragmentCountriesListBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val NO_INTERNET_CONNECTION = "No internet connection."
private const val NO_UPDATE_INFORMATION = "No update information"

class CountriesListFragment : Fragment() {
    private lateinit var binding: FragmentCountriesListBinding
    private lateinit var countryDao: CountryDao
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountriesListBinding.inflate(inflater, container, false)

        val countryRepository = CountryRepository()
        mainActivity = (activity as MainActivity)
        countryDao = mainActivity.db.countryDao()

        getCountries(countryRepository)

        return binding.root
    }

    private fun getCountries(countryRepository: CountryRepository) {
        mainActivity.loadingSpinner.visibility = View.VISIBLE

        context?.let {
            if (isInternetAvailable(it)) {
                getCountriesFromApi(countryRepository)
            } else {
                Snackbar.make(
                    mainActivity.mainContainer,
                    NO_INTERNET_CONNECTION,
                    Snackbar.LENGTH_LONG
                ).show()

                getCountriesFromDatabase()
            }
        }
    }

    private fun getCountriesFromApi(countryRepository: CountryRepository) {
        countryRepository.getCountries()?.enqueue(object : Callback<List<NetworkCountry>> {
            override fun onResponse(
                call: Call<List<NetworkCountry>>, response: Response<List<NetworkCountry>>
            ) {
                val countries = response.body() ?: return
                val mappedCountries: List<Country> = countries.map { it.asCountryDatabase() }

                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                val syncDate = LastSync(
                    lastUpdateOn = LocalDateTime.now().format(formatter).toString()
                )

                lifecycleScope.launch {
                    mappedCountries.forEach { countryDao.insert(it) }
                    countryDao.insertSyncDate(syncDate)
                    getCountriesFromDatabase()
                }
            }

            override fun onFailure(call: Call<List<NetworkCountry>>, t: Throwable) {
                Snackbar.make(binding.root, "Failure to load countries", Snackbar.LENGTH_LONG)
                    .show()
                mainActivity.loadingSpinner.visibility = View.GONE
            }
        })
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    private fun getCountriesFromDatabase() {
        lifecycleScope.launch {
            val res = countryDao.getAll()

            val adapter = CountryAdapter(res)
            binding.countriesList.adapter = adapter
            mainActivity.loadingSpinner.visibility = View.GONE
            binding.countryListSizeTv.text =
                context?.getString(R.string.countries_list_size, res.size)
            showLastSyncedTime()
        }
    }

    private fun showLastSyncedTime() {
        lifecycleScope.launch {
            val lastSyncedTime = countryDao.getLastUpdateDate()
            val updatedOn = lastSyncedTime?.lastUpdateOn ?: NO_UPDATE_INFORMATION
            binding.countryListUpdateTimeTv.text =
                context?.getString(R.string.countries_list_update, updatedOn)
        }
    }

}