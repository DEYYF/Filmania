package com.example.filmania.common.utils.Listeners

import com.example.filmania.Retrofit.VistoAnteriormente.VistoAnteriormente
import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Libreria
import com.example.filmania.common.Entyty.Media
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.Entyty.contenido_libreria

interface OnClickListener {

    fun onClickPelicula(pelicula: Peliculas)

    fun onTrailerClickPelicula(pelicula: Peliculas)

    fun onClickSerie(serie: Series)

    fun onTrailerClickSerie(serie: Series)

    fun onClickVistoAnteriormente(vistoAnteriormente: VistoAnteriormente)

}