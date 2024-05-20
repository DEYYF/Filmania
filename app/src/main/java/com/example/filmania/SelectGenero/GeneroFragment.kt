package com.example.filmania.SelectGenero

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmania.FilmaniaApplication
import com.example.filmania.MainActivity
import com.example.filmania.Retrofit.Genero.GeneroService
import com.example.filmania.SelectGenero.adapter.GeneroAdapter
import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Libreria
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.Entyty.contenido_libreria
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.FragmentGeneroBinding
import kotlinx.coroutines.launch

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



    }

    private fun setupRecyclerView(){

        val generoAdapter = GeneroAdapter(this)
        val generoLayoutManager = GridLayoutManager(requireContext(), 2)

        mBinding.rvGenero.layoutManager = generoLayoutManager
        mBinding.rvGenero.adapter = generoAdapter // Asignar el adaptador

        cargarGenero()
    }

    private fun cargarGenero(){

        val generoService = FilmaniaApplication.retrofit.create(GeneroService::class.java)

        lifecycleScope.launch {
            try {
                val response = generoService.getGenero()

                if (response.isSuccessful){
                    val genero = response.body()
                    val generoAdapter = mBinding.rvGenero.adapter as GeneroAdapter
                    generoAdapter.submitList(genero)
                }else{
                    Log.e("GeneroFragment", "Error al cargar los generos")
                }

            }catch (e: Exception){
                Log.e("GeneroFragment", "Error al cargar los generos", e)
            }
        }
    }

    private fun saveGeneros(context: Context, list: MutableList<Genero>) {
        val editor = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE).edit()
        editor.putLong("id_g", list[0].id)
        editor.putLong("id_g2", list[1].id)
        editor.putLong("id_g3", list[2].id)
        editor.apply()
    }

    private fun ChangeActivityMain()
    {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)

    }






    override fun onCLickGenero(genero: Genero) {
        generosSelect.add(genero)
        if (generosSelect.size == 3){
            saveGeneros(requireContext(), generosSelect)
            ChangeActivityMain()
        }
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


}


