package com.example.filmania.Tickets

import UserFragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.filmania.FilmaniaApplication
import com.example.filmania.Libreria_Contenido.Contenido_LibreriaFragment
import com.example.filmania.Preview.PreviewFragment
import com.example.filmania.Preview.Preview_Serie_Fragment
import com.example.filmania.R
import com.example.filmania.Retrofit.Peliculas.PeliculasService
import com.example.filmania.Retrofit.Series.SeriesService
import com.example.filmania.Retrofit.Usuario.UsuarioService
import com.example.filmania.Retrofit.VistoAnteriormente.VistoAnteriormente
import com.example.filmania.Retrofit.VistoAnteriormente.VistoAnteriormenteService
import com.example.filmania.Tickets.adapter.MediaAdapter
import com.example.filmania.Tickets.adapter.PeliculasAdapter
import com.example.filmania.Tickets.adapter.SeriesAdapter
import com.example.filmania.Tickets.adapter.VistoAnteriormenteAdapter
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
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.squareup.picasso.Picasso
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

        val mediaAdapter = VistoAnteriormenteAdapter(this)
        val mediaLayoutManager = LinearLayoutManager(requireContext())

        mediaLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        mBinding.rcVistoAnteriormente.layoutManager = mediaLayoutManager
        mBinding.rcVistoAnteriormente.adapter = mediaAdapter

        cargarvistoanteriormente()




    }



    private fun cargarPeliculas(){
        val generos = catchGeneroId(getUserId())

        val peliculasService = FilmaniaApplication.retrofit.create(PeliculasService::class.java)

        lifecycleScope.launch {
            try {
                val pelicula = peliculasService.getPeliculasGenero(generos[0], generos[1], generos[2])
                val peliculas = pelicula.body()
                val peliculasAdapter = mBinding.rcPeliculas.adapter as PeliculasAdapter
                peliculasAdapter.submitList(peliculas)
            }catch (e: Exception){
                Toast.makeText(requireContext(), "No hay peliculas disponibles", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarvistoanteriormente() {
        val VistoAnteriormenteService =
            FilmaniaApplication.retrofit.create(VistoAnteriormenteService::class.java)

        lifecycleScope.launch {
            try {
                val vistoAnteriormente = VistoAnteriormenteService.getVistoAnteriormente(getUserId())
                val vistoAnteriormentes = vistoAnteriormente.body()
                val mediaAdapter = mBinding.rcVistoAnteriormente.adapter as VistoAnteriormenteAdapter
                mediaAdapter.submitList(vistoAnteriormentes)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "No hay peliculas disponibles", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun cargarSeries(){
        val generos = catchGeneroId(getUserId())

        val seriesService = FilmaniaApplication.retrofit.create(SeriesService::class.java)

        lifecycleScope.launch {
            try {
                val serie = seriesService.getSeriesGenero(generos[0], generos[1], generos[2])
                val series = serie.body()
                val seriesAdapter = mBinding.rcSeries.adapter as SeriesAdapter
                seriesAdapter.submitList(series)
            }catch (e: Exception){
                Toast.makeText(requireContext(), "No hay series disponibles", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getUserId(): Long {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("userId", 0).toLong()
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

    private fun catchGeneroId(user_id: Long): MutableList<Long> {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val generoId = sharedPreferences.getLong("id_g$user_id", 0)
        val generoId2 = sharedPreferences.getLong("id_g2$user_id", 0)
        val generoId3 = sharedPreferences.getLong("id_g3$user_id", 0)

        return mutableListOf(generoId, generoId2, generoId3)
    }





    override fun onCLickGenero(genero: Genero) {
        TODO("Not yet implemented")
    }

    override fun onClickPelicula(pelicula: Peliculas) {
        savePeliid(pelicula.id)
        navigateToGeneroFragment(1)
        val VistoAnteriormenteService =
            FilmaniaApplication.retrofit.create(VistoAnteriormenteService::class.java)

        lifecycleScope.launch {
            try {
                val vistoAnteriormente = VistoAnteriormenteService.createVistoAnteriormente(getUserId(), pelicula.id)
                val vistoAnteriormentes = vistoAnteriormente.body()
                if (vistoAnteriormentes != null) {
                    cargarvistoanteriormente()
                }
                cargarvistoanteriormente()
            } catch (e: Exception) {
                Log.e("TicketsFragment", e.message.toString())
            }
        }
    }

    override fun onTrailerClickPelicula(pelicula: Peliculas) {
        TODO("Not yet implemented")
    }


    override fun onClickSerie(serie: Series) {
        saveserieid(serie.id)
        navigateToGeneroFragment(2)

        val VistoAnteriormenteService =
            FilmaniaApplication.retrofit.create(VistoAnteriormenteService::class.java)

        lifecycleScope.launch {
            try {
                val vistoAnteriormente = VistoAnteriormenteService.createVistoAnteriormente(getUserId(), serie.id)
                val vistoAnteriormentes = vistoAnteriormente.body()
                if (vistoAnteriormentes != null) {
                    cargarvistoanteriormente()
                }
                cargarvistoanteriormente()
            } catch (e: Exception) {
                Log.e("TicketsFragment", e.message.toString())
            }
        }
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
        if (vistoAnteriormente.Tipo == 1) {
            savePeliid(vistoAnteriormente.id)
            navigateToGeneroFragment(1)
        } else if (vistoAnteriormente.Tipo == 2) {
            saveserieid(vistoAnteriormente.id)
            navigateToGeneroFragment(2)
        }


    }

    override fun onClickcontenido_libreria(contenidoLibreria: contenido_libreria) {
        TODO("Not yet implemented")
    }

    override fun onClickMedia(media: Media) {
        TODO("Not yet implemented")
    }


}