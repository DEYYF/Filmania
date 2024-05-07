package com.example.filmania.common.Entyty

data class Series(
    var id: Long,
    val titulo: String,
    var descripcion: String,
    var temporadas: Int,
    var year: String,
    var trailer: String,
    var imagen: String
)
