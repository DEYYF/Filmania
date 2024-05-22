package com.example.filmania.common.Entyty

data class Series(
    var id: Long,
    val Titulo: String,
    val Descripcion: String,
    val Imagen: String,
    val Temporadas: Int,
    val Trailer: String,
    val Ano: Int,
    val Valoracion: Float
)
