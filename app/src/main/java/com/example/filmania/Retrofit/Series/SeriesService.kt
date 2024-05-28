package com.example.filmania.Retrofit.Series

import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesService {

    @GET(Constantes.BASE_URL + Constantes.SeriesGenero)
    suspend fun getSeriesGenero(@Query("id_g") id_g: Long, @Query("id_g2") id_g2: Long, @Query("id_g3") id_g3: Long): Response<MutableList<Series>>

    @GET(Constantes.BASE_URL + Constantes.Series_api)
    suspend fun getSeries(): Response<MutableList<Series>>

    @GET(Constantes.BASE_URL + Constantes.Serie)
    suspend fun getSerie(@Query("id") id: Long): Response<Series>

}