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
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.ItemPreviewBinding

class PreviewSerieAdapter(private var listener: OnClickListener): ListAdapter<Series, RecyclerView.ViewHolder>(SerieDiffCallback()){

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mBinding = ItemPreviewBinding.bind(view)
        fun setListener(serie: Series){
            with(mBinding){
                btnPlay.setOnClickListener{ listener.onClickSerie(serie) }
                btnTrailer.setOnClickListener{ listener.onLongClickSerie(serie) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_preview, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val serie = getItem(position)
        with(holder as ViewHolder){
            setListener(serie)
            with(mBinding){
                tvNombre.text = serie.Titulo
                tvDescription.text = serie.Descripcion
                tvYear.text = serie.Ano.toString()
                tvDuracion.text = serie.Temporadas.toString() + " Temporadas"
                Glide.with(context)
                    .load(serie.Imagen)
                    .centerCrop()
                    .into(ivPreview)

            }
        }
    }

    class SerieDiffCallback: DiffUtil.ItemCallback<Series>() {
        override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem == newItem
        }

    }
}