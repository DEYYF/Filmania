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
import com.example.filmania.Retrofit.VistoAnteriormente.VistoAnteriormente
import com.example.filmania.common.utils.Listeners.OnClickListener
import com.example.filmania.databinding.ItemPeliYSerieBinding

class VistoAnteriormenteAdapter(private var listener: OnClickListener): ListAdapter<VistoAnteriormente, RecyclerView.ViewHolder>(VistoAnteriormenteDiffCallback()) {

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mBinding = ItemPeliYSerieBinding.bind(view)

        fun setListener(vistoAnteriormente: VistoAnteriormente){
            with(mBinding){
                cvPeliSerie.setOnClickListener{ listener.onClickVistoAnteriormente(vistoAnteriormente)}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_peli_y_serie, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vistoAnteriormente = getItem(position)
        with(holder as ViewHolder){
            setListener(vistoAnteriormente)
            with(mBinding){
                tvTitulo.text = vistoAnteriormente.Titulo
                Glide.with(context)
                    .load(vistoAnteriormente.Imagen)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imgPeliSerie)
            }
        }
    }

    class VistoAnteriormenteDiffCallback: DiffUtil.ItemCallback<VistoAnteriormente>(){
        override fun areItemsTheSame(oldItem: VistoAnteriormente, newItem: VistoAnteriormente): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: VistoAnteriormente, newItem: VistoAnteriormente): Boolean {
            return oldItem == newItem
        }
    }
}