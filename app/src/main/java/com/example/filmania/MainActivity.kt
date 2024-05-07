package com.example.filmania

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.filmania.Buscador.BuscadorFragment
import com.example.filmania.Libreria.LibreriaFragment
import com.example.filmania.Noticias.NoticiasFragment
import com.example.filmania.Peliculasyseries.PeliculasySeriesFragment
import com.example.filmania.Tickets.TicketsFragment
import com.example.filmania.databinding.ActivityMain2Binding
import com.google.android.material.bottomappbar.BottomAppBar

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMain2Binding

    private lateinit var mActiveFragment: Fragment

    private lateinit var mFragmentManager: FragmentManager




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupBottomNav()

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

}