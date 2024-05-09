package com.example.filmania.Tickets

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmania.Preview.PreviewFragment
import com.example.filmania.Preview.Preview_Serie_Fragment
import com.example.filmania.SelectGenero.GeneroFragment
import com.example.filmania.Tickets.adapter.PeliculasAdapter
import com.example.filmania.Tickets.adapter.SeriesAdapter
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.FragmentTicketsBinding


class TicketsFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentTicketsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentTicketsBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()


    }

    private fun setupRecyclerView(){

        val peliculasAdapter = PeliculasAdapter(this)
        val peliculasLayoutManager = LinearLayoutManager(requireContext())

        peliculasLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        mBinding.rcPeliculas.layoutManager = peliculasLayoutManager
        mBinding.rcPeliculas.adapter = peliculasAdapter // Asignar el adaptador

        val seriesAdapter = SeriesAdapter(this)
        val seriesLayoutManager = LinearLayoutManager(requireContext())

        seriesLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        mBinding.rcSeries.layoutManager = seriesLayoutManager
        mBinding.rcSeries.adapter = seriesAdapter // Asignar el adaptador

        cargarPeliculas() // Llamar a cargarPeliculas después de asignar el adaptador
        cargarSeries() // Llamar a cargarSeries después de asignar el adaptador
    }



    private fun cargarPeliculas(){
        val peliculas = mutableListOf<Peliculas>()

        peliculas.add(Peliculas(1, "The Dark Knight", "Batman raises the stakes in his war on crime. With the help of Lt. Jim Gordon and District Attorney Harvey Dent", "1 hora y 30 Minutos", "2008", "","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/qJ2tW6WMUDux911r6m7haRef0WH.jpg"))
        peliculas.add(Peliculas(2, "The Godfather", "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family.", "2 horas y 30 Minutos", "1972", "","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/3bhkrj58Vtu7enYsRolD1fZdja1.jpg"))

        val peliculasAdapter = mBinding.rcPeliculas.adapter as PeliculasAdapter
        peliculasAdapter.submitList(peliculas)
    }


    private fun cargarSeries(){
        val series = mutableListOf<Series>()

        series.add(Series(1, "The Mandalorian", "The Mandalorian es una serie de televisión web de espacio de ciencia ficción estadounidense que se estrenó en Disney+ el 12 de noviembre de 2019. Ambientada en el universo de Star Wars, la serie tiene lugar cinco años después de los eventos de Return of the Jedi y sigue a un solitario pistolero más allá de los límites de la Nueva República.", 2, "2019","" ,"https://www.themoviedb.org/t/p/w600_and_h900_bestv2/9k9Hwq8qMMs0DhB9z5AdkTzrUu1.jpg"))
        series.add(Series(2, "The Witcher", "The Witcher es una serie de televisión web de drama de fantasía polaca-estadounidense. Basada en la serie de libros de The Witcher del autor polaco Andrzej Sapkowski, la serie fue desarrollada por Lauren Schmidt Hissrich. La serie se estrenó en Netflix el 20 de diciembre de 2019.", 2, "2019", "","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/zrPpUlehQaBf8YX2NrVrKK8IEcA.jpg"))

        val seriesAdapter = mBinding.rcSeries.adapter as SeriesAdapter
        seriesAdapter.submitList(series)

    }

    private fun saveserieid(serie: Series){
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("serieId", serie.id)
        editor.apply()
    }

    private fun savePeliid(pelicula: Peliculas){
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("peliId", pelicula.id)
        editor.apply()
    }

    private fun navigateToGeneroFragment(int: Int) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (int == 1) {
            val fragment = PreviewFragment()
            fragmentTransaction.add(android.R.id.content, fragment)
        } else if (int == 2) {
            val fragment = Preview_Serie_Fragment()
            fragmentTransaction.add(android.R.id.content, fragment)
        }

        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }




    override fun onClickSerie(serie: Series) {
        saveserieid(serie)
        navigateToGeneroFragment(2)

    }

    override fun onLongClickSerie(serie: Series) {
        TODO("Not yet implemented")
    }

    override fun onClickPelicula(pelicula: Peliculas) {
        savePeliid(pelicula)
        navigateToGeneroFragment(1)
    }

    override fun onLongClickPelicula(pelicula: Peliculas) {
        TODO("Not yet implemented")
    }

    override fun onClickNoticias(noticias: Noticias) {
        TODO("Not yet implemented")
    }

    override fun onCLickGenero(genero: Genero) {
        TODO("Not yet implemented")
    }

}