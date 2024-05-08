package com.example.filmania.DetallesNoticia.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmania.DetallesNoticia.adapter.DetalleAdapter.DetalleAdapter
import com.example.filmania.R
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.FragmentDetalleBinding

class DetalleFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentDetalleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDetalleBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SetupRecyclerView()

        mBinding.btnBack.setOnClickListener {
            navigateToGeneroFragment()
        }
    }

    private fun SetupRecyclerView(){

        val detalleAdapter = DetalleAdapter()
        val detalleLayoutManager = LinearLayoutManager(requireContext())

        detalleLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        mBinding.rcDetalle.layoutManager = detalleLayoutManager
        mBinding.rcDetalle.adapter = detalleAdapter
    }

    private fun navigateToGeneroFragment() {
        navigateBack()
    }


    private fun navigateBack() {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack()
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
        TODO("Not yet implemented")
    }


}