package com.example.filmania.Peliculasyseries

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmania.Preview.PreviewFragment
import com.example.filmania.Preview.Preview_Serie_Fragment
import com.example.filmania.Tickets.adapter.PeliculasAdapter
import com.example.filmania.Tickets.adapter.SeriesAdapter
import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Libreria
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.Entyty.contenido_libreria
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.FragmentPeliculasySeriesBinding

class PeliculasySeriesFragment : Fragment(), OnClickListener {


    private lateinit var mBinding: FragmentPeliculasySeriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentPeliculasySeriesBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView(){

        val peliculasAdapter = PeliculasAdapter(this)
        val peliculasLayoutManager = GridLayoutManager(requireContext(), 2)


        mBinding.rcPeliculas.layoutManager = peliculasLayoutManager
        mBinding.rcPeliculas.adapter = peliculasAdapter

        val seriesAdapter = SeriesAdapter(this)
        val seriesLayoutManager = GridLayoutManager(requireContext(), 2)


        mBinding.rcSeries.layoutManager = seriesLayoutManager
        mBinding.rcSeries.adapter = seriesAdapter

        cargarPeliculas()
        cargarSeries()
    }



    private fun cargarPeliculas(){
        val peliculas = mutableListOf<Peliculas>()

        val peliculasAdapter = mBinding.rcPeliculas.adapter as PeliculasAdapter
        peliculasAdapter.submitList(peliculas)
    }


    private fun cargarSeries(){
        val series = mutableListOf<Series>()



        val seriesAdapter = mBinding.rcSeries.adapter as SeriesAdapter
        seriesAdapter.submitList(series)

    }

    private fun saveserieid(serie: Series){
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("serieId", serie.Id)
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

    override fun onCLickGenero(genero: Genero) {
        TODO("Not yet implemented")
    }


    override fun onClickPelicula(pelicula: Peliculas) {
        savePeliid(pelicula)
        navigateToGeneroFragment(1)
    }

    override fun onLongClickPelicula(pelicula: Peliculas) {
        TODO("Not yet implemented")
    }

    override fun onClickSerie(serie: Series) {
        saveserieid(serie)
        navigateToGeneroFragment(2)
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


}