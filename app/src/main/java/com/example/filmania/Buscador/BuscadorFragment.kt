package com.example.filmania.Buscador

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Libreria
import com.example.filmania.common.Entyty.Media
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.Entyty.contenido_libreria
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.FragmentBuscadorBinding

class BuscadorFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentBuscadorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentBuscadorBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setupRecyclerView()
    }

    override fun onCLickGenero(genero: Genero) {
        TODO("Not yet implemented")
    }

    override fun onClickPelicula(pelicula: Peliculas) {
        TODO("Not yet implemented")
    }

    override fun onLongClickPelicula(pelicula: Peliculas) {
        TODO("Not yet implemented")
    }

    override fun onClickSerie(serie: Series) {
        TODO("Not yet implemented")
    }

    override fun onLongClickSerie(serie: Series) {
        TODO("Not yet implemented")
    }

    override fun onClickNoticia(noticias: Noticias) {
        TODO("Not yet implemented")
    }

    override fun onClickLibreria(Libreria: Libreria) {
        TODO("Not yet implemented")
    }

    override fun onClickBusqueda(busqueda: Busqueda) {
        TODO("Not yet implemented")
    }

    override fun onClickcontenido_libreria(contenidoLibreria: contenido_libreria) {
        TODO("Not yet implemented")
    }

    override fun onClickMedia(media: Media) {
        TODO("Not yet implemented")
    }

    /* private fun setupRecyclerView() {
         // Configuración del RecyclerView de películas
         val peliculasAdapter = BuscadorPeliculasAdapter(this)
         val peliculasLayoutManager = LinearLayoutManager(requireContext())
         peliculasLayoutManager.orientation = LinearLayoutManager.VERTICAL
         mBinding.rcBuscadorPeli.layoutManager = peliculasLayoutManager
         mBinding.rcBuscadorPeli.adapter = peliculasAdapter

         // Configuración del RecyclerView de series
         val seriesAdapter = BuscadorSeriesAdapter(this)
         val seriesLayoutManager = LinearLayoutManager(requireContext())
         seriesLayoutManager.orientation = LinearLayoutManager.VERTICAL
         mBinding.rcBuscadorSerie.layoutManager = seriesLayoutManager
         mBinding.rcBuscadorSerie.adapter = seriesAdapter

         // Cargar datos de películas y series
         cargarPeliculas()
         cargarSeries()
     }*/

   /* private fun cargarPeliculas() {
        val peliculas = mutableListOf<Pelicula>()

        // Agregar películas a la lista
        peliculas.add(Pelicula(1, "The Dark Knight", "Batman raises the stakes in his war on crime. With the help of Lt. Jim Gordon and District Attorney Harvey Dent", "1 hora y 30 Minutos", "2008", "","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/qJ2tW6WMUDux911r6m7haRef0WH.jpg"))
        peliculas.add(Pelicula(2, "The Godfather", "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family.", "2 horas y 30 Minutos", "1972", "","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/3bhkrj58Vtu7enYsRolD1fZdja1.jpg"))

        // Enviar la lista de películas al adaptador
        val peliculasAdapter = mBinding.rcBuscadorPeli.adapter as BuscadorPeliculasAdapter
        peliculasAdapter.submitList(peliculas)
    }*/

    /*private fun cargarSeries() {
        val series = mutableListOf<Serie>()

        // Agregar series a la lista
        series.add(Serie(1, "The Mandalorian", "The Mandalorian es una serie de televisión web de espacio de ciencia ficción estadounidense que se estrenó en Disney+ el 12 de noviembre de 2019. Ambientada en el universo de Star Wars, la serie tiene lugar cinco años después de los eventos de Return of the Jedi y sigue a un solitario pistolero más allá de los límites de la Nueva República.", 2, "2019","" ,"https://www.themoviedb.org/t/p/w600_and_h900_bestv2/9k9Hwq8qMMs0DhB9z5AdkTzrUu1.jpg"))
        series.add(Serie(2, "The Witcher", "The Witcher es una serie de televisión web de drama de fantasía polaca-estadounidense. Basada en la serie de libros de The Witcher del autor polaco Andrzej Sapkowski, la serie fue desarrollada por Lauren Schmidt Hissrich. La serie se estrenó en Netflix el 20 de diciembre de 2019.", 2, "2019", "","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/zrPpUlehQaBf8YX2NrVrKK8IEcA.jpg"))

        // Enviar la lista de series al adaptador
        val seriesAdapter = mBinding.rcBuscadorSerie.adapter as BuscadorSeriesAdapter
        seriesAdapter.submitList(series)
    }*/

}
