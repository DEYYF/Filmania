package com.example.filmania.Retrofit.Countrys

import com.example.filmania.common.Entyty.Country

data class CountryResponse(
    val error: Boolean,
    val msg: String,
    val data: MutableList<Country>
)