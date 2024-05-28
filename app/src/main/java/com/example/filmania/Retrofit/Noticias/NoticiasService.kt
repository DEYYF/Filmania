package com.example.filmania.Retrofit.Noticias

import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticiasService {

    @GET(Constantes.BASE_URL + Constantes.Noticias_api)
    suspend fun getNoticias(): Response<MutableList<Noticias>>

    @GET(Constantes.BASE_URL + Constantes.NoticiasGenero)
    suspend fun getNoticiasGenero(@Query("id_g") id_g: Long, @Query("id_g2") id_g2: Long, @Query("id_g3") id_g3: Long): Response<MutableList<Noticias>>


    @GET(Constantes.BASE_URL + Constantes.Noticia_api)
    suspend fun getNoticia(@Query("id") id: Long): Response<Noticias>
}