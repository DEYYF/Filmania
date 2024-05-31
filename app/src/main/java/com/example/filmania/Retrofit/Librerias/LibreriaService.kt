package com.example.filmania.Retrofit.Librerias

import com.example.filmania.common.Entyty.Libreria
import com.example.filmania.common.Entyty.LibreriaRequest
import com.example.filmania.common.Entyty.contenido_libreria
import com.example.filmania.common.utils.Constantes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LibreriaService {

        @GET(Constantes.BASE_URL + Constantes.Librerias)
        suspend fun getLibreriaId(@Query("id_libreria") id_libreria: Long): Response<Libreria>

        @GET(Constantes.BASE_URL + Constantes.Librerias_api)
        suspend fun getLibreriaUser(@Query("id_user") id_user: Long): Response<MutableList<Libreria>>

        @GET(Constantes.BASE_URL + Constantes.contenido_libreria_api)
        suspend fun getContenidoLibreria(@Query("id_libreria") id_libreria: Long): Response<MutableList<contenido_libreria>>

        @POST(Constantes.BASE_URL + Constantes.añadir_Contenido_libreria)
        suspend fun addContenidoLibreria(@Query("id_lib") id_lib: Long, @Query("id_med") id_med: Long): Response<MutableList<POSTADDCONTENIDO>>

        @POST(Constantes.BASE_URL + Constantes.NewLibreria + "/{id_usuario}")
        suspend fun newLibreria(@Path("id_usuario") id_usuario: Long, @Body libreriaRequest: LibreriaRequest): Response<Libreria>

        @DELETE(Constantes.BASE_URL + Constantes.delLibreria)
        suspend fun delLibreria(@Query("id_libreria") id_libreria: Long): Response<Libreria>

        @POST(Constantes.BASE_URL + Constantes.addFavoritolibreria)
        suspend fun addFavoritoLibreria(@Query("id_user") id_user: Long, @Query("id_media") id_media: Long): Response<MutableList<Libreria>>

        @POST(Constantes.BASE_URL + Constantes.addVerMasTardelibreria)
        suspend fun addVerMasTardeLibreria(@Query("id_user") id_user: Long, @Query("id_media") id_media: Long): Response<MutableList<Libreria>>

        @POST(Constantes.BASE_URL + Constantes.CambioConfiguracionLibreria + "/{id_libreria}")
        suspend fun CambioConfiguracionLibreria(@Path ("id_libreria") id_libreria: Long, @Body libreriaRequest: LibreriaRequest): Response<Libreria>

}