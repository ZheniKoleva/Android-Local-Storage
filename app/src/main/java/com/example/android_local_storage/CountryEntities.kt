package com.example.android_local_storage

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @PrimaryKey val alpha2Code: String,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="capital") val capital: String?,
    @ColumnInfo(name="flag") val flag: String,
    @ColumnInfo(name="region") val region: String,
    @ColumnInfo(name="population") val population: Int,
    @ColumnInfo(name="area") val area: Double
)

fun Country.asCountryDetails(context: Context): CountryViewDetails {
    return CountryViewDetails(
        name = context.getString(R.string.country_name_formatted, this.name),
        capital = context.getString(R.string.country_capital_formatted, this.capital),
        flag = this.flag,
        region = context.getString(R.string.country_region_formatted, this.region),
        population = context.getString(R.string.country_population_formatted, this.population),
        area = context.getString(R.string.country_area_formatted, this.area)
    )
}

@Entity
data class LastSync(
    @PrimaryKey
    @ColumnInfo(name="updateDate") val lastUpdateOn: String
)