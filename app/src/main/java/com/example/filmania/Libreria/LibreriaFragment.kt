package com.example.filmania.Libreria

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmania.FilmaniaApplication
import com.example.filmania.Libreria.adapter.LibreriaAdapter
import com.example.filmania.Libreria_Contenido.Contenido_LibreriaFragment
import com.example.filmania.R
import com.example.filmania.Retrofit.Librerias.LibreriaService
import com.example.filmania.common.Entyty.*
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.DialogAddContentBinding
import com.example.filmania.databinding.FragmentLibreriaBinding
import kotlinx.coroutines.launch

class LibreriaFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentLibreriaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentLibreriaBinding.inflate(inflater, container, false)

        mBinding.fab.setOnClickListener {
            showAddContentDialog()
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun showAddContentDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_content, null)
        val mBindingDialog = DialogAddContentBinding.bind(dialogView)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Añadir") { _, _ ->
                val titulo = mBindingDialog.editText1.text.toString()
                val imagen = mBindingDialog.editText2.text.toString()

                if (titulo.isNotEmpty() && imagen.isNotEmpty()) {
                    val libreriaService = FilmaniaApplication.retrofit.create(LibreriaService::class.java)

                    lifecycleScope.launch {
                        val response = libreriaService.newLibreria(getUserId(), LibreriaRequest(titulo, imagen))
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Libreria añadida", Toast.LENGTH_SHORT).show()
                            cargarLibreria()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }

    private fun getUserId(): Long {
        val sharedPref = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        return sharedPref.getInt("userId", 0).toLong()
    }

    private fun setupRecyclerView() {
        val adapter = LibreriaAdapter(this)
        val libreriaLayoutManager = LinearLayoutManager(requireContext())
        mBinding.rvLibrerias.layoutManager = libreriaLayoutManager
        mBinding.rvLibrerias.adapter = adapter

        cargarLibreria()
    }

    private fun cargarLibreria() {
        val libreriaService = FilmaniaApplication.retrofit.create(LibreriaService::class.java)

        lifecycleScope.launch {
            val response = libreriaService.getLibreriaUser(getUserId())
            if (response.isSuccessful) {
                val libreria = response.body()
                val adapter = mBinding.rvLibrerias.adapter as LibreriaAdapter
                adapter.submitList(libreria)
            }
        }
    }

    private fun changeContenido_libreria() {
        val fragment = Contenido_LibreriaFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.hostFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun savelibreriaid(id: Long) {
        val sharedPref = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putLong("libreriaId", id)
        editor.apply()
    }

    override fun onCLickGenero(genero: Genero) {
        // Implementar funcionalidad
    }

    override fun onClickPelicula(pelicula: Peliculas) {
        // Implementar funcionalidad
    }

    override fun onLongClickPelicula(pelicula: Peliculas) {
        // Implementar funcionalidad
    }

    override fun onClickSerie(serie: Series) {
        // Implementar funcionalidad
    }

    override fun onLongClickSerie(serie: Series) {
        // Implementar funcionalidad
    }

    override fun onClickNoticia(noticias: Noticias) {
        // Implementar funcionalidad
    }

    override fun onClickLibreria(Libreria: Libreria) {
        savelibreriaid(Libreria.id)
        changeContenido_libreria()
    }

    override fun onClickLibreriaDelete(Libreria: Libreria) {

        val libreriaService = FilmaniaApplication.retrofit.create(LibreriaService::class.java)

        lifecycleScope.launch {
            val response = libreriaService.delLibreria(Libreria.id)
            if (response.isSuccessful) {
                Toast.makeText(requireContext(), "Libreria eliminada", Toast.LENGTH_SHORT).show()
                cargarLibreria()
            }
        }
    }

    override fun onClickBusqueda(busqueda: Busqueda) {
        // Implementar funcionalidad
    }

    override fun onClickBusquedaAdd(busqueda: Busqueda) {
        TODO("Not yet implemented")
    }

    override fun onClickBusquedafav(busqueda: Busqueda) {
        // Implementar funcionalidad
    }

    override fun onClickBusquedaVerMasTarde(busqueda: Busqueda) {
        // Implementar funcionalidad
    }

    override fun onClickcontenido_libreria(contenidoLibreria: contenido_libreria) {
        // Implementar funcionalidad
    }

    override fun onClickMedia(media: Media) {
        // Implementar funcionalidad
    }
}