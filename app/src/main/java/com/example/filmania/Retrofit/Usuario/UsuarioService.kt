package com.example.filmania.Retrofit.Usuario

import com.example.filmania.common.Entyty.LogUser
import com.example.filmania.common.Entyty.Usuario
import com.example.filmania.common.Entyty.Usuario_nuevo
import com.example.filmania.common.Entyty.Usuario_raw
import com.example.filmania.common.utils.Constantes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UsuarioService {

    @POST(Constantes.BASE_URL + Constantes.Register)
    suspend fun registerUser(@Body data: Usuario_nuevo): Response<Usuario_nuevo>

    @POST(Constantes.BASE_URL + Constantes.Log)
    suspend fun loginUser(@Body data: Usuario_raw): Response<LogUser>

    @GET(Constantes.BASE_URL + Constantes.User)
    suspend fun getUser(@Query("id") id:Long): Response<Usuario>

    @DELETE(Constantes.BASE_URL + Constantes.User)
    suspend fun deleteUser(@Query("id") id:Long): Response<Usuario_Update>

    @POST(Constantes.BASE_URL + Constantes.User + "/{id_usuario}")
    suspend fun updateUser(@Path("id_usuario") id_usuario: Long, @Body data: Usuario_config): Response<Usuario>
}