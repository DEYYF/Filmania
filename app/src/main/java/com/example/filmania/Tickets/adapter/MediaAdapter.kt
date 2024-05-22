package com.example.filmania.Tickets.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.filmania.R
import com.example.filmania.common.Entyty.Media
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.ItemPeliYSerieBinding

class MediaAdapter(private var listener: OnClickListener): ListAdapter<Media, RecyclerView.ViewHolder>(MediaDiffCallback()){

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mBinding = ItemPeliYSerieBinding.bind(view)

        fun setListener(media: Media){
            with(mBinding){
                cvPeliSerie.setOnClickListener{ listener.onClickMedia(media)}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_peli_y_serie, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val media = getItem(position)
        with(holder as ViewHolder){
            setListener(media)
            with(mBinding){
                tvTitulo.text = media.titulo
                Glide.with(context)
                    .load(media.imagen)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imgPeliSerie)
            }
        }
    }

    class MediaDiffCallback: DiffUtil.ItemCallback<Media>(){
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem == newItem
        }

    }
}