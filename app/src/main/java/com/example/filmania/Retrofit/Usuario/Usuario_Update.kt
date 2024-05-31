package com.example.filmania.Retrofit.Usuario

import com.google.gson.annotations.SerializedName

data class Usuario_Update(
    @SerializedName("id") val id: Int,
    @SerializedName("usuarios") val usuarios: String,
    @SerializedName("password") val password: String,
    @SerializedName("email") val email: String,
    @SerializedName("genero") val genero: String,
    @SerializedName("pais") val pais: String,
    @SerializedName("imagen") val imagen: String
)
