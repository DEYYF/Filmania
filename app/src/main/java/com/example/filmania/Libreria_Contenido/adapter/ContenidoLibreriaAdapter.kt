package com.example.filmania.Libreria_Contenido.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmania.R
import com.example.filmania.common.Entyty.contenido_libreria
import com.example.filmania.common.utils.Listeners.OnClickListener
import com.example.filmania.databinding.ItemContenidoBinding

class ContenidoLibreriaAdapter(): ListAdapter<contenido_libreria, RecyclerView.ViewHolder>(ContenidoLibreriaDiffCallback()){

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mBinding = ItemContenidoBinding.bind(view)

        fun setListener(contenido_libreria: contenido_libreria){
            with(mBinding){

            }
        }
    }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val contenido_libreria = getItem(position)
            with(holder as ViewHolder){
                setListener(contenido_libreria)
                with(mBinding){
                    tvTitulo.text = contenido_libreria.Titulo
                    Glide.with(context)
                        .load(contenido_libreria.Imagen)
                        .centerCrop()
                        .into(imgPeliSerie)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            context = parent.context

            val view = LayoutInflater.from(context).inflate(R.layout.item_contenido, parent, false)

            return ViewHolder(view)
        }

        class ContenidoLibreriaDiffCallback: DiffUtil.ItemCallback<contenido_libreria>() {
            override fun areItemsTheSame(oldItem: contenido_libreria, newItem: contenido_libreria): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: contenido_libreria, newItem: contenido_libreria): Boolean {
                return oldItem == newItem
            }
        }
}