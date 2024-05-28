package com.example.filmania

import android.app.Application
import com.example.filmania.common.utils.Constantes
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FilmaniaApplication: Application() {

    companion object {
        lateinit var retrofit: Retrofit
        lateinit var Genero_: String
        lateinit var Anio_: String
    }

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        Genero_ = ""
        Anio_ = ""
    }
}