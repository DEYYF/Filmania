package com.example.filmania

import PreviewImageFragment
import UserFragment
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.filmania.Buscador.BuscadorFragment
import com.example.filmania.Libreria.LibreriaFragment
import com.example.filmania.Noticia.NoticiasFragment
import com.example.filmania.Peliculasyseries.PeliculasySeriesFragment
import com.example.filmania.Retrofit.Usuario.UsuarioService
import com.example.filmania.Tickets.TicketsFragment
import com.example.filmania.databinding.ActivityMain2Binding
import com.google.android.material.bottomappbar.BottomAppBar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMain2Binding

    private lateinit var mActiveFragment: Fragment

    private lateinit var mFragmentManager: FragmentManager




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupBottomNav()
        cargarUsuario()

        mBinding.ivSettings.setOnClickListener {
            navigatetoUserFragment()
        }

        mBinding.ivPerfil.setOnClickListener {
            gotoPreviewImageFragment()
        }

    }

    private fun setupBottomNav() {

        mFragmentManager = supportFragmentManager
        val ticketsFragment = TicketsFragment()
        val peliculasySeriesFragment = PeliculasySeriesFragment()
        val buscadorFragment = BuscadorFragment()
        val noticiasFragment = NoticiasFragment()
        val libreriaFragment = LibreriaFragment()


        mActiveFragment = ticketsFragment
        mFragmentManager.beginTransaction().add(
            R.id.hostFragment, peliculasySeriesFragment, PeliculasySeriesFragment::class.java.name
        ).hide(peliculasySeriesFragment).commit()

        mFragmentManager.beginTransaction().add(
            R.id.hostFragment, buscadorFragment, BuscadorFragment::class.java.name
        ).hide(buscadorFragment).commit()

        mFragmentManager.beginTransaction().add(
            R.id.hostFragment, noticiasFragment, NoticiasFragment::class.java.name
        ).hide(noticiasFragment).commit()

        mFragmentManager.beginTransaction().add(
            R.id.hostFragment, libreriaFragment, LibreriaFragment::class.java.name
        ).hide(libreriaFragment).commit()

        mFragmentManager.beginTransaction().add(
            R.id.hostFragment, ticketsFragment, TicketsFragment::class.java.name
        ).commit()

        mBinding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.action_ticket -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(ticketsFragment).commit()
                    mActiveFragment = ticketsFragment
                    true
                }
                R.id.action_play -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(peliculasySeriesFragment).commit()
                    mActiveFragment = peliculasySeriesFragment
                    true
                }
                R.id.action_search -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(buscadorFragment).commit()
                    mActiveFragment = buscadorFragment
                    true
                }
                R.id.action_news -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(noticiasFragment).commit()
                    mActiveFragment = noticiasFragment
                    true
                }
                R.id.action_library -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(libreriaFragment).commit()
                    mActiveFragment = libreriaFragment
                    true
                }
                else -> false
            }
        }








    }

    private fun cargarUsuario() {
        val usuarioService = FilmaniaApplication.retrofit.create(UsuarioService::class.java)

        lifecycleScope.launch {
            try {
                val user = usuarioService.getUser(getUserId())
                val usuario = user.body()
                if (usuario != null) {
                    Picasso.get().load(usuario.imagen).into(mBinding.ivPerfil)
                } else {
                    Picasso.get().load(R.drawable.ic_account_circle_24).into(mBinding.ivPerfil)
                }
            }catch (e: Exception){
                Log.e("TicketsFragment", e.message.toString())
            }
        }
    }

    private fun getUserId(): Long {
        val sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("userId", 0).toLong()
    }

    private fun navigatetoUserFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment = UserFragment()
        fragmentTransaction.add(android.R.id.content, fragment)

        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun gotoPreviewImageFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment = PreviewImageFragment()
        fragmentTransaction.add(android.R.id.content, fragment)

        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}