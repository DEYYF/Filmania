package com.example.filmania.Retrofit.Busqueda

import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET

interface BusquedaService {

    @GET(Constantes.BASE_URL + Constantes.Busqueda_api)
    suspend fun getBusqueda(): Response<MutableList<Busqueda>>
}