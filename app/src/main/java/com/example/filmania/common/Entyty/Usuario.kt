package com.example.filmania.common.Entyty

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id") val id: Long,
    @SerializedName("usuarios") val usuarios: String,
    @SerializedName("password") val password: String,
    @SerializedName("email") val email: String,
    @SerializedName("genero") val genero: String,
    @SerializedName("pais") val pais: String,
    @SerializedName("imagen") val imagen: String
)
