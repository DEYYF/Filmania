package com.example.filmania.common.Entyty

data class Noticias(
    var id: Long,
    val titulo: String,
    var descripcion: String,
    var img: String,
    var genero : MutableList<Genero>
)
