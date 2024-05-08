package com.example.filmania.Retrofit.Countrys

import retrofit2.Response
import retrofit2.http.GET

interface CountryService {

    @GET("countries/flag/images")
    suspend fun getData(): Response<CountryResponse>
}