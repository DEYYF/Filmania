package com.example.filmania.Preview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filmania.R
import com.example.filmania.Tickets.TicketsFragment
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.FragmentPreviewBinding


class PreviewFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentPreviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentPreviewBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnBack.setOnClickListener {
            navigateToGeneroFragment()
        }


    }


    private fun navigateToGeneroFragment() {
        navigateBack()
    }

    // Function to navigate back to the previous fragment
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