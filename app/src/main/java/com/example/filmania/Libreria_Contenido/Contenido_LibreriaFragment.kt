package com.example.filmania.Libreria_Contenido

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmania.FilmaniaApplication
import com.example.filmania.Libreria_Contenido.adapter.ContenidoLibreriaAdapter
import com.example.filmania.Retrofit.Librerias.LibreriaService
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
import com.example.filmania.databinding.FragmentContenidoLibreriaBinding
import kotlinx.coroutines.launch

class Contenido_LibreriaFragment : Fragment() {

    private lateinit var mBinding: FragmentContenidoLibreriaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentContenidoLibreriaBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        mBinding.btnBack.setOnClickListener {
            goBack()
        }

    }

    private fun setupRecyclerView() {

        val adapter_2 = ContenidoLibreriaAdapter()
        val contenidoLibreriaAdapter = LinearLayoutManager(requireContext())

        mBinding.rvContenido.layoutManager = contenidoLibreriaAdapter
        mBinding.rvContenido.adapter = adapter_2
        contenidoLibreria()

    }

    private fun  contenidoLibreria() {
        val libreriaService = FilmaniaApplication.retrofit.create(LibreriaService::class.java)

        lifecycleScope.launch {
            val response = libreriaService.getContenidoLibreria(getIdLibreria())
            if (response.isSuccessful) {
                val contenidoLibreria = response.body()
                if (contenidoLibreria != null) {
                    val contenidoLibreriaAdapter = mBinding.rvContenido.adapter as ContenidoLibreriaAdapter
                    contenidoLibreriaAdapter.submitList(contenidoLibreria)
                }
            }
        }
    }

    private fun getIdLibreria(): Long{
        val sharedPref = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        return sharedPref.getLong("libreriaId", 0)
    }

    private fun goBack() {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack()
    }



}