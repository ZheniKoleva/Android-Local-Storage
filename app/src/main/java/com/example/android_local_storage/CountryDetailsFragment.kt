package com.example.android_local_storage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.android_local_storage.databinding.FragmentCountryDetailsBinding
import kotlinx.coroutines.launch

class CountryDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCountryDetailsBinding
    private lateinit var args: CountryDetailsFragmentArgs
    private lateinit var mainActivity: MainActivity
    private lateinit var countryDao: CountryDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryDetailsBinding.inflate(inflater, container, false)
        args = navArgs<CountryDetailsFragmentArgs>().value
        mainActivity = (activity as MainActivity)
        countryDao = mainActivity.db.countryDao()

        lifecycleScope.launch {
            mainActivity.loadingSpinner.visibility = View.VISIBLE

            val searchedCountry = countryDao.getCountryById(args.countryId)

            binding.apply {
                this.country = searchedCountry.asCountryDetails(binding.root.context)

                Glide.with(root.context)
                    .load(country?.flag)
                    .placeholder(R.drawable.animation_loader)
                    .error(R.drawable.ic_twotone_cloud_off_24)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(ivFlag)
            }

            mainActivity.loadingSpinner.visibility = View.GONE
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}