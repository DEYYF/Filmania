package com.example.filmania.Noticia

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmania.DetallesNoticia.adapter.DetalleFragment
import com.example.filmania.Noticia.adapter.NoticiasAdapter
import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Libreria
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.Entyty.contenido_libreria
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.FragmentNoticiasBinding
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NoticiasFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentNoticiasBinding
    private lateinit var noticiasAdapter: NoticiasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentNoticiasBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noticiasAdapter = NoticiasAdapter(this)

        mBinding.rcNoticias.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = noticiasAdapter
        }
        cargarTodas()

        mBinding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            Log.d("NoticiasFragment", "Chip seleccionado: ${chip.text}")
            if (chip.text == "Todas") {
                cargarTodas()
            } else {
                cargarRecomendados()
            }
        }
    }

    private fun cargarRecomendados() {
        val allNoticias = getAllNoticias()
        val genero1 = getGenero1()
        val genero2 = getGenero2()
        val genero3 = getGenero3()




        //noticiasAdapter.submitList()
    }

    private fun cargarTodas() {
        val allnoticias = getAllNoticias()
        noticiasAdapter.submitList(allnoticias)
    }

    private fun getAllNoticias(): MutableList<Noticias> {
        val allNoticias = mutableListOf<Noticias>()

        return allNoticias
    }


    private fun getGenero1(): Long {
        val sharedPreferences =
            requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val genero1 = sharedPreferences.getLong("id_g", 0)

        return genero1
    }

    private fun getGenero2(): Long {
        val sharedPreferences =
            requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val genero2 = sharedPreferences.getLong("id_g2", 0)

        return genero2
    }

    private fun getGenero3(): Long {
        val sharedPreferences =
            requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val genero3 = sharedPreferences.getLong("id_g3", 0)

        return genero3
    }



    private fun saveNoticiasId(noticias: Noticias) {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("noticias_id", noticias.id)
        editor.apply()
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
        saveNoticiasId(noticias)
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = DetalleFragment()
        fragmentTransaction.replace(android.R.id.content, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
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