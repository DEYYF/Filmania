package com.example.filmania.Preview

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmania.FilmaniaApplication
import com.example.filmania.Preview.Adapter.PreviewAdapter
import com.example.filmania.Preview.Adapter.PreviewSerieAdapter
import com.example.filmania.Retrofit.Series.SeriesService
import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Libreria
import com.example.filmania.common.Entyty.Media
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.Entyty.contenido_libreria
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.FragmentPreviewSerieBinding
import kotlinx.coroutines.launch

class Preview_Serie_Fragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentPreviewSerieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentPreviewSerieBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnBack.setOnClickListener {
            navigateToGeneroFragment()
        }

        setupRecyclerView()
    }




    private fun navigateToGeneroFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack()
    }



    private fun setupRecyclerView() {
        val PreviewAdapter = PreviewSerieAdapter(this)
        val PreviewLayoutManager = LinearLayoutManager(requireContext())
        mBinding.rcPreview.apply {
            adapter = PreviewAdapter
            layoutManager = PreviewLayoutManager
        }
        cargarSerie()
    }


    private fun cargarSerie() {

        val seriesService = FilmaniaApplication.retrofit.create(SeriesService::class.java)

        lifecycleScope.launch {
            try {
                val response = seriesService.getSerie(catchSerieId())
                if (response.isSuccessful) {
                    val serie = response.body()
                    val PreviewAdapter = mBinding.rcPreview.adapter as PreviewSerieAdapter
                    PreviewAdapter.submitList(serie)

                }
            }catch (e: Exception){
                Log.e("Preview_Serie_Fragment", e.message.toString())
            }
        }
    }

    private fun catchSerieId(): Long {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val serie_id = sharedPreferences.getLong("serieId", 0)

        return serie_id
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


}