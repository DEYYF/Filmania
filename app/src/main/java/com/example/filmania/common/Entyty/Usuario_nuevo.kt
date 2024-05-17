package com.example.filmania.common.Entyty

import com.google.gson.annotations.SerializedName

data class Usuario_nuevo(
    @SerializedName("Usuarios") val Usuarios: String,
    @SerializedName("password") val password: String,
    @SerializedName("Email") val Email: String,
    @SerializedName("Genero") val Genero: String,
    @SerializedName("Pais") val Pais: String,
    @SerializedName("Imagen") val Imagen: String
)
