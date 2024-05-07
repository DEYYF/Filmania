package com.example.filmania.common.Entyty

import com.google.gson.annotations.SerializedName

data class UsuarioNuevo(
    @SerializedName("username") val Username: String,
    @SerializedName("password") val Password: String,
    @SerializedName("email") val email: String,
    @SerializedName("genero") val gender: String,
    @SerializedName("pais") val Pais: String,
    @SerializedName("imagen") val Imagen: String
)
