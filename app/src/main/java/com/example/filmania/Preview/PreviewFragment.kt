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
import com.example.filmania.Retrofit.Peliculas.PeliculasService
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.databinding.FragmentPreviewBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


class PreviewFragment : Fragment() {

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

        setupRecyclerView()


    }


    private fun navigateToGeneroFragment() {
        navigateBack()

    }

    private fun setupRecyclerView() {

        mBinding.btnPlay.setOnClickListener {
            onClickVer()
        }

        val peli_id = catchpeliId()
        val service = FilmaniaApplication.retrofit.create(PeliculasService::class.java)

        lifecycleScope.launch {
            val response = service.getPelicula(peli_id)

            if (response.isSuccessful) {
                val peliculas = response.body()!!
                mBinding.tvNombre.text = peliculas.Titulo
                mBinding.tvDescription.text = peliculas.Descripcion
                mBinding.tvYear.text = peliculas.Ano.toString()
                mBinding.tvDuracion.text = peliculas.Duracion .toString() + " Minutos"
                Picasso.get().load(peliculas.Imagen).into(mBinding.ivPreview)


            }else{
                Log.e("previewFragment", response.errorBody().toString())
            }
        }
    }



    private fun catchpeliId(): Long {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val peli_id = sharedPreferences.getLong("peliId", 0)

        return peli_id
    }

    private fun navigateBack() {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack()
    }

    private fun onClickVer() {
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
        val url = FilmaniaApplication.listCines.random()
        intent.data = android.net.Uri.parse(url)
        startActivity(intent)
    }

    private fun onClickTrailer(peliculas: Peliculas) {
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
        intent.data = android.net.Uri.parse(peliculas.Trailer)
        startActivity(intent)
    }




}