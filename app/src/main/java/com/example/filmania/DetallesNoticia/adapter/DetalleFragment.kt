package com.example.filmania.DetallesNoticia.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmania.FilmaniaApplication
import com.example.filmania.Retrofit.Noticias.NoticiasService
import com.example.filmania.Retrofit.VistoAnteriormente.VistoAnteriormente
import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Libreria
import com.example.filmania.common.Entyty.Media
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.Entyty.contenido_libreria
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.FragmentDetalleBinding
import kotlinx.coroutines.launch

class DetalleFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentDetalleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDetalleBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SetupRecyclerView()

        mBinding.btnBack.setOnClickListener {
            navigateToGeneroFragment()
        }
    }

    private fun SetupRecyclerView(){

        val detalleAdapter = DetalleAdapter(null)
        val detalleLayoutManager = LinearLayoutManager(requireContext())

        detalleLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        mBinding.rcDetalle.layoutManager = detalleLayoutManager
        mBinding.rcDetalle.adapter = detalleAdapter

        cargarNoticia()


    }

    private fun navigateToGeneroFragment() {
        navigateBack()
    }


    private fun navigateBack() {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack()
    }

    private fun cargarNoticia() {
        val noticiasService = FilmaniaApplication.retrofit.create(NoticiasService::class.java)

        lifecycleScope.launch {
            val response = noticiasService.getNoticia(getNoticiashared())
            if (response.isSuccessful) {
                val noticia = response.body()
                if (noticia != null) {
                    val detalleAdapter = DetalleAdapter(noticia)
                    mBinding.rcDetalle.adapter = detalleAdapter
                    detalleAdapter.updateData(noticia)
                }
            }
        }
    }

    private fun getNoticiashared(): Long {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val noticia = sharedPreferences.getLong("noticias_id", 0)

        return noticia
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