package com.example.android_local_storage

data class Country(
    val name: String,
    val capital: String,
    val flags: Flag
)

data class Flag(
    val svg: String,
    val png: String
)