package com.example.filmania.Noticias

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
import com.example.filmania.Noticias.adapter.NoticiasAdapter
import com.example.filmania.R
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.Entyty.Usuario
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
        val generosSeleccionados = getGenerosFromSharedPreferences()

        val recomendadas = allNoticias.filter { noticia ->
            noticia.genero.any { generoNoticia ->
                generosSeleccionados.any { generoSeleccionado ->
                    generoSeleccionado.id == generoNoticia.id
                }
            }
        }
        noticiasAdapter.submitList(recomendadas)
    }

    private fun cargarTodas() {
        val allnoticias = getAllNoticias()
        noticiasAdapter.submitList(allnoticias)
    }

    private fun getAllNoticias(): MutableList<Noticias> {
        val allNoticias = mutableListOf<Noticias>()

        allNoticias.add(Noticias(1, "Noticia 1", "df","https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg", generos()))
        allNoticias.add(Noticias(2, "Noticia 2", "df","https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg", generos()))
        allNoticias.add(Noticias(3, "Noticia 3", "df","https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg", generos()))
        allNoticias.add(Noticias(4, "Noticia 4", "df","https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg", generos()))
        allNoticias.add(Noticias(5, "Noticia 5", "df","https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg", generos()))
        allNoticias.add(Noticias(6, "Noticia 6", "df","https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg", generos()))

        return allNoticias
    }

    private fun generos(): MutableList<Genero> {
        val genero = mutableListOf<Genero>()
        genero.add(Genero(1, "Accion", "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg"))
        genero.add(Genero(2, "Aventura", "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg"))

        return genero
    }

    private fun getGenerosFromSharedPreferences(): MutableList<Genero> {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("generos", "")
        val type = object : TypeToken<MutableList<Genero>>() {}.type
        return gson.fromJson(json, type) ?: mutableListOf()
    }

    private fun saveNoticiasId(noticias: Noticias) {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("noticias_id", noticias.id)
        editor.apply()
    }



    override fun onClickSerie(serie: Series) {
        TODO("Not yet implemented")
    }

    override fun onLongClickSerie(serie: Series) {
        TODO("Not yet implemented")
    }

    override fun onClickPelicula(pelicula: Peliculas) {
        TODO("Not yet implemented")
    }

    override fun onLongClickPelicula(pelicula: Peliculas) {
        TODO("Not yet implemented")
    }

    override fun onClickNoticias(noticias: Noticias) {
        saveNoticiasId(noticias)
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = DetalleFragment()
        fragmentTransaction.replace(android.R.id.content, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onCLickGenero(genero: Genero) {
        TODO("Not yet implemented")
    }
}