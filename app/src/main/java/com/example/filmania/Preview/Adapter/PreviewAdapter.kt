package com.example.filmania.Preview.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmania.R
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.ItemPreviewBinding

class PreviewAdapter(private var listener: OnClickListener): ListAdapter<Peliculas, RecyclerView.ViewHolder>(PeliculaDiffCallback()) {

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mBinding = ItemPreviewBinding.bind(view)
        fun setListener(pelicula: Peliculas){
            with(mBinding){
                btnPlay.setOnClickListener{ listener.onClickPelicula(pelicula) }
                btnTrailer.setOnClickListener{ listener.onLongClickPelicula(pelicula) }
            }
        }
    }






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_preview, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pelicula = getItem(position)
        with(holder as ViewHolder){
            setListener(pelicula)
            with(mBinding){
                tvNombre.text = pelicula.Titulo
                tvDescription.text = pelicula.Descripcion
                tvYear.text = pelicula.Ano.toString()
                tvDuracion.text = pelicula.Duracion.toString() + " min"
                Glide.with(context)
                    .load(pelicula.Imagen)
                    .centerCrop()
                    .into(ivPreview)

            }
        }
    }

    class PeliculaDiffCallback: DiffUtil.ItemCallback<Peliculas>() {
        override fun areItemsTheSame(oldItem: Peliculas, newItem: Peliculas): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Peliculas, newItem: Peliculas): Boolean {
            return oldItem == newItem
        }

    }


}