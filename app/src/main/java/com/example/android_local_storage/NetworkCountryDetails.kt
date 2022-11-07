package com.example.android_local_storage

import android.content.Context

data class NetworkCountryDetails(
    val name: String,
    val capital: String,
    val flags: Flag,
    val region: String,
    val population: Int,
    val area: Double,
)

fun NetworkCountryDetails.asCountryDetails(context: Context): CountryViewDetails {
    return CountryViewDetails(
        name = context.getString(R.string.country_name_formatted, this.name),
        capital = context.getString(R.string.country_capital_formatted, this.capital),
        flag = this.flags.png,
        region = context.getString(R.string.country_region_formatted, this.region),
        population = context.getString(R.string.country_population_formatted, this.population),
        area = context.getString(R.string.country_area_formatted, this.area)
    )
}
