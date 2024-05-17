package com.example.filmania.common.Entyty

data class Peliculas(
    var id: Long,
    val titulo: String,
    val descripcion: String,
    val imagen: String,
    val duracion: Int,
    val trailer: String,
    val ano: Int,
    val valoracion: Float
)
