package com.example.filmania.Preview.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmania.R
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.ItemPreviewBinding

class PreviewSerieAdapter(private val listener: OnClickListener, private var serie: Series?) : RecyclerView.Adapter<PreviewSerieAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mBinding = ItemPreviewBinding.bind(view)
        fun setListener(serie: Series?) {
            with(mBinding) {
                serie?.let { s ->
                    btnPlay.setOnClickListener { listener.onClickSerie(s) }
                    btnTrailer.setOnClickListener { listener.onLongClickSerie(s) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_preview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        serie?.let { s ->
            with(holder.mBinding) {
                tvNombre.text = s.Titulo
                tvDescription.text = s.Descripcion
                tvYear.text = s.Ano.toString()
                tvDuracion.text = s.Temporadas.toString() + " Temporadas"
                Glide.with(context)
                    .load(s.Imagen)
                    .centerCrop()
                    .into(ivPreview)
            }
        }
        holder.setListener(serie)
    }

    override fun getItemCount(): Int {
        return if (serie != null) 1 else 0
    }

    fun updateSerie(newSerie: Series?) {
        serie = newSerie
        notifyDataSetChanged()
    }
}
