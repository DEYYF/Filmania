package com.example.filmania.Peliculasyseries

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmania.FilmaniaApplication
import com.example.filmania.Preview.PreviewFragment
import com.example.filmania.Preview.Preview_Serie_Fragment
import com.example.filmania.Retrofit.Peliculas.PeliculasService
import com.example.filmania.Retrofit.Series.SeriesService
import com.example.filmania.Retrofit.VistoAnteriormente.VistoAnteriormente
import com.example.filmania.Tickets.adapter.PeliculasAdapter
import com.example.filmania.Tickets.adapter.SeriesAdapter
import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Libreria
import com.example.filmania.common.Entyty.Media
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.Entyty.contenido_libreria
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.FragmentPeliculasySeriesBinding
import kotlinx.coroutines.launch

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

        cargarPeliculas()

        val seriesAdapter = SeriesAdapter(this)
        val seriesLayoutManager = GridLayoutManager(requireContext(), 2)


        mBinding.rcSeries.layoutManager = seriesLayoutManager
        mBinding.rcSeries.adapter = seriesAdapter


        cargarSeries()
    }



    private fun cargarPeliculas(){
        val peliculasService = FilmaniaApplication.retrofit.create(PeliculasService::class.java)

        lifecycleScope.launch {
            try {
                val response = peliculasService.getPeliculas()
                if (response.isSuccessful) {
                    val peliculas = response.body()
                    if (peliculas != null) {
                        val peliculasAdapter = mBinding.rcPeliculas.adapter as PeliculasAdapter
                        peliculasAdapter.submitList(peliculas)
                    }
                }
            } catch (e: Exception) {
                Log.e("Peliculas", e.message.toString())
            }
        }
    }


    private fun cargarSeries(){
        val seriesService = FilmaniaApplication.retrofit.create(SeriesService::class.java)

        lifecycleScope.launch {
            try {
                val response = seriesService.getSeries()
                if (response.isSuccessful) {
                    val series = response.body()
                    if (series != null) {
                        val seriesAdapter = mBinding.rcSeries.adapter as SeriesAdapter
                        seriesAdapter.submitList(series)
                    }
                }
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
        }
    }

    private fun saveserieid(serieid: Long){
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("serieId", serieid)
        editor.apply()
    }

    private fun savePeliid(peliculaid: Long){
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("peliId", peliculaid)
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
        savePeliid(pelicula.id)
        navigateToGeneroFragment(1)
    }

    override fun onTrailerClickPelicula(pelicula: Peliculas) {
        TODO("Not yet implemented")
    }

    override fun onClickSerie(serie: Series) {
        saveserieid(serie.id)
        navigateToGeneroFragment(2)
    }

    override fun onTrailerClickSerie(serie: Series) {
        TODO("Not yet implemented")
    }

    override fun onClickNoticia(noticias: Noticias) {
        TODO("Not yet implemented")
    }

    override fun onClickLibreria(Libreria: Libreria) {
        TODO("Not yet implemented")
    }

    override fun onClickLibreriaDelete(Libreria: Libreria) {
        TODO("Not yet implemented")
    }


    override fun onClickBusqueda(busqueda: Busqueda) {
        TODO("Not yet implemented")
    }

    override fun onClickBusquedaAdd(busqueda: Busqueda) {
        TODO("Not yet implemented")
    }

    override fun onClickBusquedafav(busqueda: Busqueda) {
        TODO("Not yet implemented")
    }

    override fun onClickBusquedaVerMasTarde(busqueda: Busqueda) {
        TODO("Not yet implemented")
    }

    override fun onClickVistoAnteriormente(vistoAnteriormente: VistoAnteriormente) {
        TODO("Not yet implemented")
    }

    override fun onClickcontenido_libreria(contenidoLibreria: contenido_libreria) {
        TODO("Not yet implemented")
    }

    override fun onClickMedia(media: Media) {
        TODO("Not yet implemented")
    }


}