package com.example.filmania.Buscador

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.filmania.Retrofit.VistoAnteriormente.VistoAnteriormenteService
import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.utils.Listeners.OnClickListenerBusqueda
import com.example.filmania.databinding.FragmentBuscadorBinding
import kotlinx.coroutines.launch
import retrofit2.Response

class BuscadorFragment : Fragment(), OnClickListenerBusqueda {

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

    private fun navigateToGeneroFragment(int: Int) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (int == 1) {
            val fragment = PreviewFragment()
            fragmentTransaction.add(android.R.id.content, fragment)
        } else if (int == 2) {
            val fragment = Preview_Serie_Fragment()
            fragmentTransaction.add(android.R.id.content, fragment)
        }

        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


    override fun onClickBusqueda(busqueda: Busqueda) {

        Log.e("Busqueda", busqueda.toString())
        if (busqueda.Tipo == "Pelicula"){
            savePeliid(busqueda.id)
            val vistoAnteriormenteService = FilmaniaApplication.retrofit.create(VistoAnteriormenteService::class.java)
            lifecycleScope.launch{
                val response = vistoAnteriormenteService.createVistoAnteriormente(getUserId(), busqueda.id)
                if(response.isSuccessful){
                    Log.e("Pelicula", "VistoAnteriormente creado")
                }else{
                    Log.e("Pelicula", "Error al crear VistoAnteriormente")
                }
            }

            navigateToGeneroFragment(1)


        }else if (busqueda.Tipo == "Serie"){
            saveserieid(busqueda.id)

            val vistoAnteriormenteService = FilmaniaApplication.retrofit.create(VistoAnteriormenteService::class.java)
            lifecycleScope.launch {
                val response =
                    vistoAnteriormenteService.createVistoAnteriormente(getUserId(), busqueda.id)
                if (response.isSuccessful) {
                    Log.e("VistoAnteriormente", "VistoAnteriormente creado")
                } else {
                    Log.e("VistoAnteriormente", "Error al crear VistoAnteriormente")

                }
            }
            navigateToGeneroFragment(2)
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


}
