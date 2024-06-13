package com.example.filmania.common.utils.Listeners

import com.example.filmania.common.Entyty.Busqueda

interface OnClickListenerBusqueda {

    fun onClickBusqueda(busqueda: Busqueda)
    fun onClickBusquedaAdd(busqueda: Busqueda)
    fun onClickBusquedafav(busqueda: Busqueda)
    fun onClickBusquedaVerMasTarde(busqueda: Busqueda)
}