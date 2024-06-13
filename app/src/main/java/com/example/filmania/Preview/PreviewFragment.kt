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
import com.example.filmania.Retrofit.Peliculas.PeliculasService
import com.example.filmania.Retrofit.VistoAnteriormente.VistoAnteriormente
import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Libreria
import com.example.filmania.common.Entyty.Media
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.Entyty.contenido_libreria
import com.example.filmania.common.utils.Listeners.OnClickListener
import com.example.filmania.databinding.FragmentPreviewBinding
import kotlinx.coroutines.launch
import kotlin.math.log


class PreviewFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentPreviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentPreviewBinding.inflate(inflater, container, false)

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
        navigateBack()

    }

    private fun setupRecyclerView() {
        val adapter = PreviewAdapter(this, null)
        val previewLayoutManager = LinearLayoutManager(requireContext())

        mBinding.rcPreview.layoutManager = previewLayoutManager
        mBinding.rcPreview.adapter = adapter

        cargarpelicula()
    }

    private fun cargarpelicula() {
        val peli_id = catchpeliId()
        val service = FilmaniaApplication.retrofit.create(PeliculasService::class.java)

        lifecycleScope.launch {
            val response = service.getPelicula(peli_id)

            if (response.isSuccessful) {
                val pelicula = response.body()
                val previewAdapter = mBinding.rcPreview.adapter as PreviewAdapter
                previewAdapter.updatePelicula(pelicula)
                Log.e("previewFragment", pelicula.toString())
                Log.e("previewFragment", response.body().toString())

            }else{
                Log.e("previewFragment", response.errorBody().toString())
            }
        }
    }

    private fun catchpeliId(): Long {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val peli_id = sharedPreferences.getLong("peliId", 0)

        return peli_id
    }

    private fun navigateBack() {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack()
    }


    override fun onClickPelicula(pelicula: Peliculas) {
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
        val url = FilmaniaApplication.listCines.random()
        intent.data = android.net.Uri.parse(url)
        startActivity(intent)
    }

    override fun onTrailerClickPelicula(pelicula: Peliculas) {
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
        intent.data = android.net.Uri.parse(pelicula.Trailer)
        startActivity(intent)
    }

    override fun onClickSerie(serie: Series) {
        TODO("Not yet implemented")
    }

    override fun onTrailerClickSerie(serie: Series) {
        TODO("Not yet implemented")
    }


    override fun onClickVistoAnteriormente(vistoAnteriormente: VistoAnteriormente) {
        TODO("Not yet implemented")
    }


}