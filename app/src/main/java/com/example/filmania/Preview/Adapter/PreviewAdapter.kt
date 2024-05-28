package com.example.filmania.Preview.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmania.R
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.ItemPreviewBinding

class PreviewAdapter(private val listener: OnClickListener, private var pelicula: Peliculas?) : RecyclerView.Adapter<PreviewAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mBinding = ItemPreviewBinding.bind(view)
        fun setListener(pelicula: Peliculas?) {
            with(mBinding) {
                pelicula?.let { p ->
                    btnPlay.setOnClickListener { listener.onClickPelicula(p) }
                    btnTrailer.setOnClickListener { listener.onLongClickPelicula(p) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_preview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        pelicula?.let { p ->
            with(holder.mBinding) {
                tvNombre.text = p.Titulo
                tvDescription.text = p.Descripcion
                tvYear.text = p.Ano.toString()
                tvDuracion.text = p.Duracion.toString() + " min"
                Glide.with(context)
                    .load(p.Imagen)
                    .centerCrop()
                    .into(ivPreview)
            }
        }
        holder.setListener(pelicula)
    }

    override fun getItemCount(): Int {
        return if (pelicula != null) 1 else 0
    }

    fun updatePelicula(newPelicula: Peliculas?) {
        pelicula = newPelicula
        notifyDataSetChanged()
    }
}
