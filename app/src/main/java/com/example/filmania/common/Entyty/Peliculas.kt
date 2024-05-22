package com.example.filmania.common.Entyty

data class Peliculas(
    var id: Long,
    val Titulo: String,
    val Descripcion: String,
    val Imagen: String,
    val Duracion: Int,
    val Trailer: String,
    val Ano: Int,
    val Valoracion: Float
)
