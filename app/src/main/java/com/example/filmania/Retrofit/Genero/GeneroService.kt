package com.example.filmania.Retrofit.Genero

import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET

interface GeneroService {

    @GET(Constantes.BASE_URL + Constantes.Generos)
    suspend fun getGenero(): Response<MutableList<Genero>>
}