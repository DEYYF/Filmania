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
        lateinit var listCines: List<String>
    }

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        Genero_ = ""
        Anio_ = ""
        listCines = listOf(
            "https://cinesmn4.com/tarifa.php", "https://www.cinesur.com/", "https://www.cinesa.es/", "https://www.yelmocines.es/", "https://www.kinepolis.es/", "https://cinesmn4.com/tarifa.php", "https://cinesmn4.com/tarifa.php", "https://cinesmn4.com/tarifa.php","https://www.yelmocines.es/", "https://www.yelmocines.es/"
        )
    }
}