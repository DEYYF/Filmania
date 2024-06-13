package com.example.filmania.Libreria.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.filmania.R
import com.example.filmania.common.Entyty.Libreria
import com.example.filmania.common.utils.Listeners.OnClickListener
import com.example.filmania.common.utils.Listeners.OnClickListenerLibreria
import com.example.filmania.databinding.ItemLibreriasBinding
import com.squareup.picasso.Picasso

class LibreriaAdapter(private var listener: OnClickListenerLibreria): ListAdapter<Libreria, RecyclerView.ViewHolder>(LibreriaDiffCallback()){

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mBinding = ItemLibreriasBinding.bind(view)

        fun setListener(libreria: Libreria){
            with(mBinding){
                 cardView.setOnClickListener{ listener.onClickLibreria(libreria) }
                btnDelete.setOnClickListener{ listener.onClickLibreriaDelete(libreria)}
            }
        }
    }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val libreria = getItem(position)
            with(holder as ViewHolder){
                setListener(libreria)
                with(mBinding){
                    tvTitle.text = libreria.titulo
                    Picasso.get().load(libreria.imagen).into(imageView)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            context = parent.context

            val view = LayoutInflater.from(context).inflate(R.layout.item_librerias, parent, false)

            return ViewHolder(view)
        }

        class LibreriaDiffCallback: DiffUtil.ItemCallback<Libreria>() {
            override fun areItemsTheSame(oldItem: Libreria, newItem: Libreria): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Libreria, newItem: Libreria): Boolean {
                return oldItem == newItem
            }
        }
    }


