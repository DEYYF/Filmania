package com.example.filmania.DetallesNoticia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmania.R
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.databinding.ItemNoticiasViewBinding

class DetalleAdapter(private var noticia: Noticias?) : RecyclerView.Adapter<DetalleAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mBinding = ItemNoticiasViewBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_noticias_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        noticia?.let {
            with(holder.mBinding) {
                tvTitulo.text = it.titulo
                tvDesc.text = it.descripcion
                Glide.with(context)
                    .load(it.imagen)
                    .centerCrop()
                    .into(ivNoticia)
            }
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    fun updateData(newNoticia: Noticias) {
        this.noticia = newNoticia
        notifyDataSetChanged()
    }
}
