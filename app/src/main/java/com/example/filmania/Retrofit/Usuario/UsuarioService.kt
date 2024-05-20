package com.example.filmania.Retrofit.Usuario

import com.example.filmania.common.Entyty.LogUser
import com.example.filmania.common.Entyty.Usuario
import com.example.filmania.common.Entyty.Usuario_nuevo
import com.example.filmania.common.Entyty.Usuario_raw
import com.example.filmania.common.utils.Constantes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioService {

    @POST(Constantes.BASE_URL + Constantes.Register)
    suspend fun registerUser(@Body data: Usuario_nuevo): Response<Usuario>

    @POST(Constantes.BASE_URL + Constantes.Log)
    suspend fun loginUser(@Body data: Usuario_raw): Response<LogUser>
}