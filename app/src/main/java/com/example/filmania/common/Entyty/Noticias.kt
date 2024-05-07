package com.example.filmania.common.Entyty

data class Noticias(
    var id: Long,
    val titulo: String,
    var descripcion: String,
    var img: String,
    var genero : MutableList<Genero>
){
    override fun toString(): String {
        return "Noticias(id=$id, titulo='$titulo', descripcion='$descripcion', img='$img', genero=$genero)"
    }
}
