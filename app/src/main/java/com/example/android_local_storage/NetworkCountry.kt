package com.example.android_local_storage

data class NetworkCountry(
    val alpha2Code: String,
    val name: String,
    val capital: String,
    val flags: Flag,
    val region: String,
    val population: Int,
    val area: Double
)

data class Flag(
    val svg: String,
    val png: String
)

 fun NetworkCountry.asCountryDatabase(): Country {
    return Country(
        alpha2Code = this.alpha2Code,
        name = this.name,
        capital = this.capital,
        population = this.population,
        area = this.area,
        flag = this.flags.png,
        region = this.region
    )
}
