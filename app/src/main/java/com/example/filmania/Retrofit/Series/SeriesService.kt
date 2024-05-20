package com.example.filmania.Retrofit.Series

import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesService {

    @GET(Constantes.BASE_URL + Constantes.SeriesGenero)
    abstract suspend fun getSeriesGenero(@Query("id_g") id_g: Long, @Query("id_g2") id_g2: Long, @Query("id_g3") id_g3: Long): Response<MutableList<Series>>
}