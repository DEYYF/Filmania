package com.example.filmania.Noticia

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmania.DetallesNoticia.adapter.DetalleFragment
import com.example.filmania.FilmaniaApplication
import com.example.filmania.Noticia.adapter.NoticiasAdapter
import com.example.filmania.Retrofit.Noticias.NoticiasService
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.utils.Listeners.OnClickListenerNoticias
import com.example.filmania.databinding.FragmentNoticiasBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

class NoticiasFragment : Fragment(), OnClickListenerNoticias {

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

        mBinding.rcNoticias.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        cargarTodas()

        mBinding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            if (chip.text == "Todas") {
                cargarTodas()
            } else {
                cargarRecomendados()
            }
        }
    }

    private fun cargarRecomendados() {
        val genero1 = catchGeneroId(getUserId())[0]
        val genero2 = catchGeneroId(getUserId())[1]
        val genero3 = catchGeneroId(getUserId())[2]

        val noticiasService = FilmaniaApplication.retrofit.create(NoticiasService::class.java)

        lifecycleScope.launch {
            try {
                val response = noticiasService.getNoticiasGenero(genero1, genero2, genero3)
                if (response.isSuccessful) {
                    val noticias = response.body()
                    noticiasAdapter = NoticiasAdapter(this@NoticiasFragment)
                    mBinding.rcNoticias.adapter = noticiasAdapter
                    noticiasAdapter.submitList(noticias)
                    noticiasAdapter.notifyDataSetChanged()
                    Log.e("NoticiasFragment", "Noticias: $noticias")
                } else {
                    Toast.makeText(requireContext(), "No hay noticias disponibles", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("NoticiasFragment", "Error al cargar las noticias", e)
            }
        }
    }

    private fun cargarTodas() {
        val noticiasService = FilmaniaApplication.retrofit.create(NoticiasService::class.java)

        lifecycleScope.launch {
            try {
                val response = noticiasService.getNoticias()
                if (response.isSuccessful) {
                    val noticias = response.body()
                    noticiasAdapter = NoticiasAdapter(this@NoticiasFragment)
                    mBinding.rcNoticias.adapter = noticiasAdapter
                    noticiasAdapter.submitList(noticias)
                    noticiasAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "No hay noticias disponibles", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("NoticiasFragment", "Error al cargar las noticias", e)
            }
        }
    }

    private fun getUserId(): Long {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("userId", 0).toLong()
    }

    private fun catchGeneroId(user_id: Long): MutableList<Long> {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val generoId = sharedPreferences.getLong("id_g$user_id", 0)
        val generoId2 = sharedPreferences.getLong("id_g2$user_id", 0)
        val generoId3 = sharedPreferences.getLong("id_g3$user_id", 0)

        return mutableListOf(generoId, generoId2, generoId3)
    }

    private fun saveNoticiasId(noticias: Noticias) {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("noticias_id", noticias.id)
        editor.apply()
    }


    override fun onClickNoticia(noticias: Noticias) {
        saveNoticiasId(noticias)
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = DetalleFragment()
        fragmentTransaction.replace(android.R.id.content, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}
