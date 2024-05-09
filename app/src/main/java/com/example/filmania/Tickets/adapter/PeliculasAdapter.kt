package com.example.filmania.Tickets.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.filmania.R
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.ItemPeliYSerieBinding

class PeliculasAdapter(private var listener: OnClickListener) : ListAdapter<Peliculas, RecyclerView.ViewHolder>(PeliculasDiffCallback()) {

        private lateinit var context: Context


        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val mBinding = ItemPeliYSerieBinding.bind(view)

            fun setListener(pelicula: Peliculas){
                with(mBinding){
                    cvPeliSerie.setOnClickListener{ listener.onClickPelicula(pelicula)}
                }
            }
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            context = parent.context

            val view = LayoutInflater.from(context).inflate(R.layout.item_peli_y_serie, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val peli = getItem(position)
            with(holder as ViewHolder){
                setListener(peli)
                with(mBinding){
                    tvTitulo.text = peli.titulo
                    val animation = AnimationUtils.loadAnimation(context, R.anim.series_pelis_text_animation)
                    tvTitulo.startAnimation(animation)

                    Glide.with(context)
                        .load(peli.imagen)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(imgPeliSerie)


                }
            }
        }


    class PeliculasDiffCallback: DiffUtil.ItemCallback<Peliculas>() {
        override fun areItemsTheSame(oldItem: Peliculas, newItem: Peliculas): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Peliculas, newItem: Peliculas): Boolean {
            return oldItem == newItem
        }

    }
}