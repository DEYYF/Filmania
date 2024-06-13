package com.example.filmania.Noticia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmania.R
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.utils.Listeners.OnClickListenerNoticias
import com.example.filmania.databinding.ItemNoticiaBinding

class NoticiasAdapter(private var listener: OnClickListenerNoticias): ListAdapter<Noticias, RecyclerView.ViewHolder>(NoticiasDiffCallback()){

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mBinding = ItemNoticiaBinding.bind(view)

        fun setListener(noticias: Noticias){
            with(mBinding){
                cvNoticia.setOnClickListener{ listener.onClickNoticia(noticias)}
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_noticia, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val noticia = getItem(position)
        with(holder as ViewHolder){
            setListener(noticia)
            with(mBinding){
                tvTituloNoticia.text = noticia.titulo
                Glide.with(context)
                    .load(noticia.imagen)
                    .centerCrop()
                    .into(imgNoticia)
            }
        }
    }

    class NoticiasDiffCallback: DiffUtil.ItemCallback<Noticias>(){
        override fun areItemsTheSame(oldItem: Noticias, newItem: Noticias): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Noticias, newItem: Noticias): Boolean {
            return oldItem == newItem
        }
    }

}