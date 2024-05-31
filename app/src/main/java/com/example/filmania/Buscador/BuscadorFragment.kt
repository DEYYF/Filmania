package com.example.filmania.Buscador

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmania.Añadir_Libreria.Add_Media_LibreriaFragment
import com.example.filmania.Buscador.adapter.BuscadorAdapter
import com.example.filmania.FilmaniaApplication
import com.example.filmania.Preview.PreviewFragment
import com.example.filmania.Preview.Preview_Serie_Fragment
import com.example.filmania.R
import com.example.filmania.Retrofit.Busqueda.BusquedaService
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
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.FragmentBuscadorBinding
import kotlinx.coroutines.launch
import retrofit2.Response

class BuscadorFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentBuscadorBinding

    private lateinit var media: MutableList<Busqueda>

    private lateinit var response: Response<MutableList<Busqueda>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentBuscadorBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        mBinding.filterButton.setOnClickListener {
            changetoFilter()
        }
    }

    private fun setupRecyclerView() {
        val adapter = BuscadorAdapter(this)
        val buscadorLayoutManager = LinearLayoutManager(requireContext())


        mBinding.rcBuscador.layoutManager = buscadorLayoutManager
        mBinding.rcBuscador.adapter = adapter

        cargarBusqueda()


    }

    private fun cargarBusqueda(){
        val busqueda = FilmaniaApplication.retrofit.create(BusquedaService::class.java)
        lifecycleScope.launch {
            if (FilmaniaApplication.Genero_ == "" && FilmaniaApplication.Anio_ == "") {
                response = busqueda.getBusqueda()
            }else if(FilmaniaApplication.Genero_ != "" && FilmaniaApplication.Anio_ == ""){
                response = busqueda.getBusquedaGenero(FilmaniaApplication.Genero_)
            }else if(FilmaniaApplication.Genero_ == "" && FilmaniaApplication.Anio_ != ""){
                response = busqueda.getBusquedaAno(FilmaniaApplication.Anio_)
            }else if (FilmaniaApplication.Genero_ != "" && FilmaniaApplication.Anio_ != ""){
                response = busqueda.getBusquedaGeneroAno(FilmaniaApplication.Anio_, FilmaniaApplication.Genero_)
            }
            if(response.isSuccessful){
                media = response.body()!!
                if(busqueda != null){
                    val busquedaAdapter = mBinding.rcBuscador.adapter as BuscadorAdapter
                    busquedaAdapter.submitList(media)
                }
            }
        }
    }


    private fun setupSearchView() {
        mBinding.searchview.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = media.filter {it.Titulo.contains(newText!!, ignoreCase = true)}

                val busquedaAdapter = mBinding.rcBuscador.adapter as BuscadorAdapter
                busquedaAdapter.submitList(filteredList)

                return true
            }
        })
    }

    private fun changetoFilter() {
        val fragment = FilterFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.hostFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getUserId() : Long{
        val sharedPref = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        return sharedPref.getInt("userId", 0).toLong()
    }

    private fun guardarBusquedaId(id: Long){
        val sharedPref = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putLong("busquedaId", id)
        editor.apply()
    }


    private fun saveserieid(serieId: Long){
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("serieId", serieId)
        editor.apply()
    }

    private fun savePeliid(id: Long) {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("peliId", id)
        editor.apply()
    }

    private fun addFavoritoToast(){
        Toast.makeText(requireContext(), "Añadido a favoritos", Toast.LENGTH_SHORT).show()
    }

    private fun addVerMasTardeToast(){
        Toast.makeText(requireContext(), "Añadido a ver más tarde", Toast.LENGTH_SHORT).show()
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
        if (busqueda.Tipo == "Película"){
            savePeliid(busqueda.id)
            val fragment = PreviewFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.hostFragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }else if (busqueda.Tipo == "Serie"){
            saveserieid(busqueda.id)
            val fragment = Preview_Serie_Fragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.hostFragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        


    }

    override fun onClickBusquedaAdd(busqueda: Busqueda) {
        guardarBusquedaId(busqueda.id)
        val fragment = Add_Media_LibreriaFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.hostFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onClickBusquedafav(busqueda: Busqueda) {

        val libreriaService = FilmaniaApplication.retrofit.create(LibreriaService::class.java)

        lifecycleScope.launch {
            val response = libreriaService.addFavoritoLibreria(getUserId(), busqueda.id)
            if(response.isSuccessful){
                addFavoritoToast()
            }
        }
    }

    override fun onClickBusquedaVerMasTarde(busqueda: Busqueda) {
        val libreriaService = FilmaniaApplication.retrofit.create(LibreriaService::class.java)

        lifecycleScope.launch {
            val response = libreriaService.addVerMasTardeLibreria(getUserId(), busqueda.id)
            if(response.isSuccessful){
                addVerMasTardeToast()
            }
        }
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
