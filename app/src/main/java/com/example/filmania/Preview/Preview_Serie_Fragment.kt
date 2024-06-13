package com.example.filmania.Preview

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.filmania.FilmaniaApplication
import com.example.filmania.Retrofit.Series.SeriesService
import com.example.filmania.common.Entyty.Series
import com.example.filmania.databinding.FragmentPreviewSerieBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class Preview_Serie_Fragment : Fragment() {

    private lateinit var mBinding: FragmentPreviewSerieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentPreviewSerieBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnBack.setOnClickListener {
            navigateToGeneroFragment()
        }





        setupRecyclerView()
    }




    private fun navigateToGeneroFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack()
    }



    private fun setupRecyclerView() {
        cargarSerie()
        mBinding.btnPlay.setOnClickListener{
            onClickVer()
        }
    }


    private fun cargarSerie() {

        val seriesService = FilmaniaApplication.retrofit.create(SeriesService::class.java)

        lifecycleScope.launch {
            try {
                val response = seriesService.getSerie(catchSerieId())
                if (response.isSuccessful) {
                    val serie = response.body()!!
                    mBinding.tvNombre.text = serie.Titulo
                    mBinding.tvDescription.text = serie.Descripcion
                    mBinding.tvYear.text = serie.Ano.toString()
                    mBinding.tvDuracion.text = serie.Temporadas.toString() + " Temporadas"
                    Picasso.get().load(serie.Imagen).into(mBinding.ivPreview)
                    mBinding.btnPlay.setOnClickListener {
                        onClickTrailer(serie)
                    }

                }
            }catch (e: Exception){
                Log.e("Preview_Serie_Fragment", e.message.toString())
            }
        }
    }

    private fun catchSerieId(): Long {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val serie_id = sharedPreferences.getLong("serieId", 0)

        return serie_id
    }
    private fun onClickVer() {
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
        val url = FilmaniaApplication.listCines.random()
        intent.data = android.net.Uri.parse(url)
        startActivity(intent)
    }

    private fun onClickTrailer(serie: Series) {
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
        intent.data = android.net.Uri.parse(serie.Trailer)
        startActivity(intent)
    }

}