package com.example.filmania.common.Entyty

data class Peliculas(
    var id: Long,
    val titulo: String,
    var descripcion: String,
    var duracion: String,
    var year: String,
    var trailer: String,
    var imagen: String
)
