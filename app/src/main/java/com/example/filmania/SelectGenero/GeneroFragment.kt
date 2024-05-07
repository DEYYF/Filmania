package com.example.filmania.SelectGenero

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmania.MainActivity
import com.example.filmania.SelectGenero.adapter.GeneroAdapter
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.FragmentGeneroBinding
import com.google.gson.Gson
import kotlin.math.log

class GeneroFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentGeneroBinding

    private var generosSelect: MutableList<Genero> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentGeneroBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        mBinding.btnGenero.setOnClickListener {
            saveList(requireContext(), generosSelect)
            ChangeActivityMain()
            Log.e("Generos", generosSelect.toString())
        }

    }

    private fun setupRecyclerView(){

        val generoAdapter = GeneroAdapter(this)
        val generoLayoutManager = GridLayoutManager(requireContext(), 2)

        mBinding.rvGenero.layoutManager = generoLayoutManager
        mBinding.rvGenero.adapter = generoAdapter // Asignar el adaptador

        cargarGenero()
    }

    private fun cargarGenero(){
        val genero = mutableListOf<Genero>()
        genero.add(Genero(1, "Accion", "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg"))
        genero.add(Genero(2, "Aventura", "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg"))
        genero.add(Genero(3, "Comedia", "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg"))
        genero.add(Genero(4, "Drama", "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg"))
        genero.add(Genero(5, "Fantasia", "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg"))
        genero.add(Genero(6, "Terror", "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg"))

        val generoAdapter = mBinding.rvGenero.adapter as GeneroAdapter

        generoAdapter.submitList(genero)
    }

    private fun saveList(context: Context, list: MutableList<Genero>) {
        val gson = Gson()
        val json = gson.toJson(list)
        val editor = context.getSharedPreferences("Lista_user", Context.MODE_PRIVATE).edit()
        editor.putString("my_list", json)
        editor.apply()
    }

    private fun ChangeActivityMain()
    {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)

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
        TODO("Not yet implemented")
    }

    override fun onCLickGenero(genero: Genero) {

        if (generosSelect.size < 3) {
            if (generosSelect.contains(genero)) {
                generosSelect.remove(genero)
            } else  {
                generosSelect.add(genero)
            }
        } else {
            Toast.makeText(requireContext(), "Solo puedes seleccionar tres géneros", Toast.LENGTH_SHORT).show()
        }
    }



}


