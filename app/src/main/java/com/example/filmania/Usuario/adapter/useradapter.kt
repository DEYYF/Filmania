package com.example.filmania.Usuario.adapter

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
import com.example.filmania.databinding.ItemAccountBinding
import com.squareup.picasso.Picasso

class useradapter(private var listener: OnClickListenerLibreria) : ListAdapter<Libreria, RecyclerView.ViewHolder>(UserDiffCallback()){

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mBinding = ItemAccountBinding.bind(view)

        fun setListener(libreria: Libreria){
            with(mBinding){
                cardView.setOnClickListener{ listener.onClickLibreria(libreria)}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_account, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val media = getItem(position)
        with(holder as ViewHolder){
            setListener(media)
            with(mBinding){
                tvTitle.text = media.titulo
                Picasso.get().load(media.imagen).into(imageView)
            }
        }
    }


    class UserDiffCallback: DiffUtil.ItemCallback<Libreria>(){
        override fun areItemsTheSame(oldItem: Libreria, newItem: Libreria): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Libreria, newItem: Libreria): Boolean {
            return oldItem == newItem
        }
    }




}