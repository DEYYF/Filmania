package com.example.filmania.common.Entyty

data class Country(
    val name: String,
    val flag: String,
    val iso2: String,
    val iso3: String
) {
    override fun toString(): String {
        return name
    }
}