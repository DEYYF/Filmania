package com.example.filmania.Registro.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.filmania.R
import com.example.filmania.common.Entyty.Country
import java.net.URL

class CountrySpinnerAdapter(context: Context, countries: List<Country>) :
    ArrayAdapter<Country>(context, 0, countries) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val country = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)

        val flagImageView = view.findViewById<ImageView>(R.id.flagImageView)
        val countryNameTextView = view.findViewById<TextView>(R.id.countryNameTextView)

        countryNameTextView.text = country?.name

        // Load SVG from the internet and set it to ImageView
        Glide.with(context)
            .load(country?.flag)
            .into(flagImageView)

        return view
    }
}