package com.example.filmania.common.utils

import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series

interface OnClickListener {

    fun onClickSerie(serie: Series)

    fun onLongClickSerie(serie: Series)

    fun onClickPelicula(pelicula: Peliculas)

    fun onLongClickPelicula(pelicula: Peliculas)

    fun onClickNoticias(noticias: Noticias)

    fun onCLickGenero(genero: Genero)
}