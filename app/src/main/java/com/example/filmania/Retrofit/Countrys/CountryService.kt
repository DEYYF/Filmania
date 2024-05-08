package com.example.filmania.Retrofit.Countrys

import com.example.filmania.common.Entyty.Country
import com.example.filmania.common.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET

interface CountryService {

    @GET(Constantes.BASE_URL_COUNTRY)
    suspend fun getCountries(): Response<MutableList<Country>>
}