package com.example.filmania.Retrofit.Peliculas

import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PeliculasService {

    @GET(Constantes.BASE_URL+ Constantes.PeliculasGenero)
    suspend fun getPeliculasGenero(@Query("id_g") id_g: Long, @Query("id_g2") id_g2: Long, @Query("id_g3") id_g3: Long): Response<MutableList<Peliculas>>
}