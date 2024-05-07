package com.example.filmania.DetallesNoticia.adapter.DetalleAdapter

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
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.ItemNoticiasViewBinding

class DetalleAdapter(): ListAdapter<Noticias, RecyclerView.ViewHolder>(NoticiasDiffUtil()){

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mBinding = ItemNoticiasViewBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_noticias_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val noticia = getItem(position)
        with(holder as ViewHolder){
            with(mBinding){
                tvTitulo.text = noticia.titulo
                tvDesc.text = noticia.descripcion
                Glide.with(context)
                    .load(noticia.img)
                    .centerCrop()
                    .into(ivNoticia)
            }
        }
    }

    class NoticiasDiffUtil: DiffUtil.ItemCallback<Noticias>(){
        override fun areItemsTheSame(oldItem: Noticias, newItem: Noticias): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Noticias, newItem: Noticias): Boolean {
            return oldItem == newItem
        }
    }
}