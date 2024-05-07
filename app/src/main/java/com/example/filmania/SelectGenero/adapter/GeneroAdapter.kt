package com.example.filmania.SelectGenero.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmania.R
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.ItemGeneroBinding

class GeneroAdapter(private var listener: OnClickListener): ListAdapter<Genero, RecyclerView.ViewHolder>(
    GeneroDiffCallback()
) {

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mBinding = ItemGeneroBinding.bind(view)


        fun setListener(genero: Genero){
            with(mBinding){
                cvgenero.setOnClickListener{ listener.onCLickGenero(genero) }
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_genero, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val genero = getItem(position)
        with(holder as ViewHolder){
            setListener(genero)
            with(mBinding){
                tvGenero.text = genero.nombre
                Glide.with(context)
                    .load(genero.imagen)
                    .centerCrop()
                    .into(imvgenero)
            }
        }
    }

    class GeneroDiffCallback: DiffUtil.ItemCallback<Genero>() {
        override fun areItemsTheSame(oldItem: Genero, newItem: Genero): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genero, newItem: Genero): Boolean {
            return oldItem == newItem
        }
    }


}