package com.example.android_local_storage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.android_local_storage.databinding.CountryListItemBinding

class CountryAdapter(private val countries: List<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(val binding: CountryListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CountryListItemBinding.inflate(layoutInflater, parent, false)

        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentCountry = countries[position]
        holder.binding.apply {
            this.country = currentCountry

            Glide.with(root.context)
                .load(currentCountry.flag)
                .placeholder(R.drawable.animation_loader)
                .error(R.drawable.ic_twotone_cloud_off_24)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivFlag)

            root.setOnClickListener {
                val action = CountriesListFragmentDirections
                    .actionCountriesListFragmentToCountryDetailsFragment(currentCountry.alpha2Code)
                root.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return countries.size
    }
}