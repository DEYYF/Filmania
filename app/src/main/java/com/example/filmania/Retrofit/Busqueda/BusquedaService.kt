package com.example.filmania.Retrofit.Busqueda

import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BusquedaService {

    @GET(Constantes.BASE_URL + Constantes.Busqueda_api)
    suspend fun getBusqueda(): Response<MutableList<Busqueda>>

    @GET(Constantes.BASE_URL + Constantes.Busqueda_api + Constantes.BusquedaGenero)
    suspend fun getBusquedaGenero(@Query("genero") genero: String): Response<MutableList<Busqueda>>

    @GET(Constantes.BASE_URL + Constantes.Busqueda_api + Constantes.Busquedayear)
    suspend fun getBusquedaAno(@Query("year") year: String): Response<MutableList<Busqueda>>

    @GET(Constantes.BASE_URL + Constantes.Busqueda_api + Constantes.BusquedaGenero + Constantes.Busquedayear)
    suspend fun getBusquedaGeneroAno(@Query("year") year: String, @Query("genero") genero: String): Response<MutableList<Busqueda>>

}