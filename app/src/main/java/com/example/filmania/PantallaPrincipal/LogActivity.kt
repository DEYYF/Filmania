package com.example.filmania.PantallaPrincipal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmania.InicioSesion.IniciarSesionActivity
import com.example.filmania.Registro.RegistrarseActivity
import com.example.filmania.databinding.ActivityLoginBinding

class LogActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btIniciarSesion.setOnClickListener {
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }

        mBinding.btRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistrarseActivity::class.java)
            startActivity(intent)
        }

        mBinding.btIniciarSesionGoogle.setOnClickListener {

        }


    }




}