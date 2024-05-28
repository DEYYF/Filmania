package com.example.filmania.Buscador.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmania.R
import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.ItemBuscadorSerieBinding

class BuscadorAdapter(private var listener: OnClickListener): ListAdapter<Busqueda, RecyclerView.ViewHolder>(BusquedaDiffCallback())
{
    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mBinding = ItemBuscadorSerieBinding.bind(view)

        fun setListener(busqueda: Busqueda){
            with(mBinding){
                cvBuscadorserie.setOnClickListener{ listener.onClickBusqueda(busqueda) }
                ibtnAdd.setOnClickListener{ listener.onClickBusquedaAdd(busqueda) }
                ibtnfav.setOnClickListener{ listener.onClickBusquedafav(busqueda) }
                ibtnclock.setOnClickListener{ listener.onClickBusquedaVerMasTarde(busqueda) }
            }
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val busqueda = getItem(position)
        with(holder as ViewHolder){
            setListener(busqueda)
            with(mBinding){
                Glide.with(context)
                    .load(busqueda.Imagen)
                    .centerCrop()
                    .into(imgPeliSerie)
                tvTitulo.text = busqueda.Titulo
                tvValoracion.text = busqueda.Valoracion.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_buscador_serie, parent, false)

        return ViewHolder(view)
    }


    class BusquedaDiffCallback: DiffUtil.ItemCallback<Busqueda>() {
        override fun areItemsTheSame(oldItem: Busqueda, newItem: Busqueda): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Busqueda, newItem: Busqueda): Boolean {
            return oldItem == newItem
        }

    }
}