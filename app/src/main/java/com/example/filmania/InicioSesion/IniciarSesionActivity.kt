package com.example.filmania.InicioSesion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.filmania.MainActivity
import com.example.filmania.Registro.RegistrarseActivity
import com.example.filmania.SelectGenero.GeneroFragment
import com.example.filmania.common.Entyty.Usuario
import com.example.filmania.databinding.ActivityIniciarSesionBinding

class IniciarSesionActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityIniciarSesionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btIniciarSesion.setOnClickListener {
            LogIn()
        }

        mBinding.btRegistro.setOnClickListener {
            navigateToRegistroActivity()
        }

        Glide.with(this)
            .load("https://s3.amazonaws.com/qreatech.com/Logo+Filmania+(1).jpg")
            .into(mBinding.ivBackground)



    }

    private fun LogIn() {
        val Username = mBinding.tilUsername.editText?.text.toString()
        val Password = mBinding.tilPassword.editText?.text.toString()

        if(Username.isEmpty() || Password.isEmpty()) {
            mBinding.tilUsername.error = "Campo vacio"
            mBinding.tilPassword.error = "Campo vacio"
        }
    }

    private fun navigateToGeneroFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = GeneroFragment()
        fragmentTransaction.replace(android.R.id.content, fragment)
        fragmentTransaction.commit()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToRegistroActivity() {
        val intent = Intent(this, RegistrarseActivity::class.java)
        startActivity(intent)
    }


    private fun saveIsFirstTime(isFirstTime: Boolean, userId: Int) {
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isFirstTime_$userId", isFirstTime)
        editor.apply()
    }

    private fun getIsFirstTime(userId: Int): Boolean {
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isFirstTime_$userId", true)
    }

    private fun saveUserId(user: Usuario ) {
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("userId", user.id)
        editor.apply()
    }



}