package com.example.filmania.common.utils

import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Libreria
import com.example.filmania.common.Entyty.Media
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.Entyty.contenido_libreria

interface OnClickListener {



    fun onCLickGenero(genero: Genero)

    fun onClickPelicula(pelicula: Peliculas)

    fun onLongClickPelicula(pelicula: Peliculas)

    fun onClickSerie(serie: Series)

    fun onLongClickSerie(serie: Series)

    fun onClickNoticia(noticias: Noticias)

    fun onClickLibreria(Libreria: Libreria)

    fun onClickBusqueda(busqueda: Busqueda)

    fun onClickcontenido_libreria(contenidoLibreria: contenido_libreria)

    fun onClickMedia(media: Media)
}