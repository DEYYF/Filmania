package com.example.filmania.Añadir_Libreria

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmania.Añadir_Libreria.adapter.adapterAdd_Media_Libreria
import com.example.filmania.FilmaniaApplication
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
import com.example.filmania.common.utils.Listeners.OnClickListenerLibreria
import com.example.filmania.databinding.FragmentAddMediaLibreriaBinding
import kotlinx.coroutines.launch

class Add_Media_LibreriaFragment : Fragment(), OnClickListenerLibreria {

    private lateinit var binding: FragmentAddMediaLibreriaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMediaLibreriaBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        binding.btnBack.setOnClickListener {
            goBusquedaFragment()
        }
    }


    private fun getUserId(): Long {
        val sharedPref = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        return sharedPref.getInt("userId", 0).toLong()
    }

    private fun getBusquedaId(): Long {
        val sharedPref = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        return sharedPref.getLong("busquedaId", 0L)
    }

    private fun setupRecyclerView() {
        val adapter = adapterAdd_Media_Libreria(this)
        val librerialayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = librerialayoutManager
        binding.recyclerView.adapter = adapter

        cargarLibreria()
    }

    private fun cargarLibreria() {
        val libreriaService = FilmaniaApplication.retrofit.create(LibreriaService::class.java)

        lifecycleScope.launch {
            val response = libreriaService.getLibreriaUser(getUserId())
            if (response.isSuccessful) {
                val libreria = response.body()
                val adapter = binding.recyclerView.adapter as adapterAdd_Media_Libreria
                adapter.submitList(libreria)
            } else {
                Toast.makeText(requireContext(), "Error al cargar la libreria", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goBusquedaFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack()
    }



    override fun onClickLibreria(Libreria: Libreria) {
        val libreriaService = FilmaniaApplication.retrofit.create(LibreriaService::class.java)

        lifecycleScope.launch {
            val response = libreriaService.addContenidoLibreria(Libreria.id, getBusquedaId())
            if (response.isSuccessful) {
                Toast.makeText(requireContext(), "Añadido a la libreria", Toast.LENGTH_SHORT).show()
                goBusquedaFragment()
            } else {
                Toast.makeText(requireContext(), "Error al añadir a la libreria", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClickLibreriaDelete(Libreria: Libreria) {
        TODO("Not yet implemented")
    }


}
