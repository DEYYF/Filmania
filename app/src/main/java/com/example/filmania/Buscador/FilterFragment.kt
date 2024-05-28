package com.example.filmania.Buscador

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.filmania.FilmaniaApplication
import com.example.filmania.R
import com.example.filmania.Retrofit.Genero.GeneroService
import com.example.filmania.databinding.FragmentFilterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilterFragment : Fragment() {

    private lateinit var mBinding: FragmentFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFilterBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.buttonApplyFilter.setOnClickListener {
            saveFilter()
            changetoBuscadorFragment()
        }

        mBinding.buttonDeleteFilter.setOnClickListener {
            clearFilter()
            changetoBuscadorFragment()
        }

        lifecycleScope.launch {
            cargargeneros()
        }

        // Set initial values from FilmaniaApplication
        mBinding.editTextYear.setText(FilmaniaApplication.Anio_)
    }

    private fun saveFilter() {
        val selectedGenre = mBinding.spinnerGenre.selectedItem
        val yearText = mBinding.editTextYear.text

        if (selectedGenre != null && selectedGenre.toString().isNotEmpty()) {
            FilmaniaApplication.Genero_ = selectedGenre.toString()
        } else {
            FilmaniaApplication.Genero_ = ""
        }
        if (yearText != null) {
            FilmaniaApplication.Anio_ = yearText.toString()
        } else {
            FilmaniaApplication.Anio_ = ""
        }
    }

    private fun clearFilter() {
        FilmaniaApplication.Genero_ = ""
        FilmaniaApplication.Anio_ = ""
        mBinding.spinnerGenre.setSelection(0)
        mBinding.editTextYear.text!!.clear()
    }

    private fun changetoBuscadorFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack()
    }

    private suspend fun cargargeneros() {
        val generoService = FilmaniaApplication.retrofit.create(GeneroService::class.java)
        try {
            val response = generoService.getGenero()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val generos = response.body()
                    if (generos != null) {
                        val nombresGeneros = mutableListOf("").apply {
                            addAll(generos.map { it.nombre })
                        }
                        val generosAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombresGeneros)
                        mBinding.spinnerGenre.adapter = generosAdapter
                        // Set the spinner to the saved genre if it exists, otherwise set to ""
                        val savedGenre = FilmaniaApplication.Genero_
                        val position = if (savedGenre.isNotEmpty()) nombresGeneros.indexOf(savedGenre) else 0
                        mBinding.spinnerGenre.setSelection(position)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Error", e.toString())
        }
    }
}
