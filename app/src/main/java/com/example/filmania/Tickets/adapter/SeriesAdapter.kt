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
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.ItemPeliYSerieBinding

class SeriesAdapter(private var listener: OnClickListener) : ListAdapter<Series, RecyclerView.ViewHolder>(SeriesDiffCallback())
{

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val mBinding = ItemPeliYSerieBinding.bind(view)

        fun setListener(serie: Series)
        {
            with(mBinding){
                cvPeliSerie.setOnClickListener { listener.onClickSerie(serie) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_peli_y_serie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        val serie = getItem(position)

        with(holder as ViewHolder)
        {
            setListener(serie)
            with(mBinding)
            {
                tvTitulo.text = serie.Titulo
                val animation = AnimationUtils.loadAnimation(context, R.anim.series_pelis_text_animation)
                tvTitulo.startAnimation(animation)
                Glide.with(context)
                    .load(serie.Imagen)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imgPeliSerie)
            }
        }

    }


    class SeriesDiffCallback: DiffUtil.ItemCallback<Series>()
    {
        override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean
        {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean
        {
            return oldItem == newItem
        }

    }
}
