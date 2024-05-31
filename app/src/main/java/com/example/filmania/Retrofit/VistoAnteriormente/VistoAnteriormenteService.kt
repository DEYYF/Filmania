package com.example.filmania.Retrofit.VistoAnteriormente

import com.example.filmania.common.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface VistoAnteriormenteService {

    @GET(Constantes.BASE_URL + Constantes.VistoAnteriormente)
    suspend fun getVistoAnteriormente(@Query("id_user") id_user: Long): Response<List<VistoAnteriormente>>

    @POST(Constantes.BASE_URL + Constantes.VistoAnteriormeteCreate)
    suspend fun createVistoAnteriormente(@Query("id_user") id_user: Long, @Query("id_media") id_media: Long): Response<VistoAnteriormente>
}