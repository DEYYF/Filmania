package com.example.filmania.Tickets

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmania.FilmaniaApplication
import com.example.filmania.Preview.PreviewFragment
import com.example.filmania.Preview.Preview_Serie_Fragment
import com.example.filmania.Retrofit.Peliculas.PeliculasService
import com.example.filmania.Retrofit.Series.SeriesService
import com.example.filmania.Tickets.adapter.MediaAdapter
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
import com.example.filmania.databinding.FragmentTicketsBinding
import com.google.gson.Gson
import kotlinx.coroutines.launch


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

        val mediaAdapter = MediaAdapter(this)
        val mediaLayoutManager = LinearLayoutManager(requireContext())

        mediaLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        mBinding.rcVistoAnteriormente.layoutManager = mediaLayoutManager
        mBinding.rcVistoAnteriormente.adapter = mediaAdapter

    }



    private fun cargarPeliculas(){
        val generos = catchGeneroId()

        val peliculasService = FilmaniaApplication.retrofit.create(PeliculasService::class.java)

        lifecycleScope.launch {
            try {
                val pelicula = peliculasService.getPeliculasGenero(generos[0], generos[1], generos[2])
                val peliculas = pelicula.body()
                val peliculasAdapter = mBinding.rcPeliculas.adapter as PeliculasAdapter
                peliculasAdapter.submitList(peliculas)
            }catch (e: Exception){
                Log.e("TicketsFragment", e.message.toString())
            }
        }
    }


    private fun cargarSeries(){
        val generos = catchGeneroId()

        val seriesService = FilmaniaApplication.retrofit.create(SeriesService::class.java)

        lifecycleScope.launch {
            try {
                val serie = seriesService.getSeriesGenero(generos[0], generos[1], generos[2])
                val series = serie.body()
                val seriesAdapter = mBinding.rcSeries.adapter as SeriesAdapter
                seriesAdapter.submitList(series)
            }catch (e: Exception){
                Log.e("TicketsFragment", e.message.toString())
            }
        }

    }

    private fun saveserieid(serieId: Long){
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("serieId", serieId)
        editor.apply()
    }

    private fun savePeliid(id: Long) {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("peliId", id)
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

    private fun catchGeneroId(): MutableList<Long> {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val generoId = sharedPreferences.getLong("id_g", 0)
        val generoId2 = sharedPreferences.getLong("id_g2", 0)
        val generoId3 = sharedPreferences.getLong("id_g3", 0)

        return mutableListOf(generoId, generoId2, generoId3)
    }



    override fun onCLickGenero(genero: Genero) {
        TODO("Not yet implemented")
    }

    override fun onClickPelicula(pelicula: Peliculas) {
        savePeliid(pelicula.id)
        navigateToGeneroFragment(1)
    }

    override fun onLongClickPelicula(pelicula: Peliculas) {
        TODO("Not yet implemented")
    }

    override fun onClickSerie(serie: Series) {
        saveserieid(serie.id)
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

    override fun onClickcontenido_libreria(contenidoLibreria: contenido_libreria) {
        TODO("Not yet implemented")
    }

    override fun onClickMedia(media: Media) {
        TODO("Not yet implemented")
    }


}