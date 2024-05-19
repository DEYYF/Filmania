package com.example.filmania.Registro

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.filmania.InicioSesion.IniciarSesionActivity
import com.example.filmania.R
import com.example.filmania.Registro.Adapter.CountrySpinnerAdapter
import com.example.filmania.Retrofit.Countrys.CountryService
import com.example.filmania.Retrofit.Usuario.UsuarioService
import com.example.filmania.common.Entyty.Country
import com.example.filmania.common.Entyty.Usuario
import com.example.filmania.common.Entyty.Usuario_nuevo
import com.example.filmania.common.utils.Constantes
import com.example.filmania.databinding.ActivityRegistrarseBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RegistrarseActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegistrarseBinding

    private lateinit var password_correcto: String

    private var pais: MutableList<Country> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        Glide.with(this)
            .load("https://s3.amazonaws.com/qreatech.com/Logo+Filmania+(1).jpg")
            .into(mBinding.ivBackground)

        mBinding.etImg.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val imgURL = s.toString()
                cargarImagen(imgURL)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No necesitamos hacer nada aquí
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No necesitamos hacer nada aquí
            }
        })



        lifecycleScope.launch { getCountries() }

        mBinding.tiPais.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCountry = parent.getItemAtPosition(position) as Country
                // Aquí puedes hacer algo con el país seleccionado

                // Cambia el color del texto a blanco cuando se selecciona un país
                (parent.getChildAt(0) as TextView).setTextColor(Color.WHITE)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Aquí puedes hacer algo cuando no se selecciona ningún país
            }
        }


        mBinding.btRegistrarse.setOnClickListener { AddNewUser() }



    }

    private fun RegisterSuccesfull()
    {
        Toast.makeText(this, R.string.succesfull, Toast.LENGTH_SHORT).show()
        ChangeActivityLogin()

    }

    private fun RegisterBad()
    {
        Toast.makeText(this, R.string.FailedString, Toast.LENGTH_SHORT).show()
    }

    suspend fun  getCountries() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://countriesnow.space/api/v0.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val countryService = retrofit.create(CountryService::class.java)

        lifecycleScope.launch {
            try {
                val response = countryService.getData()
                pais = response.body()!!.data
                pais.add(0, Country("Países", "", "", ""))


                withContext(Dispatchers.Main) {


                    val adapter = CountrySpinnerAdapter(this@RegistrarseActivity, pais)

                    mBinding.tiPais.adapter = adapter

                    mBinding.tiPais.post {
                        mBinding.tiPais.dropDownVerticalOffset = mBinding.tiPais.height
                    }

                    mBinding.tiPais.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            val selectedCountry = parent.getItemAtPosition(position) as Country
                            // Aquí puedes hacer algo con el país seleccionado
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            // Aquí puedes hacer algo cuando no se selecciona ningún país
                        }
                    }
                }
            }catch (e: Exception){
                Log.e("Error", e.message.toString())
            }
        }
    }

    private fun ChangeActivityLogin()
    {
        val intent = Intent(this, IniciarSesionActivity::class.java)
        startActivity(intent)
    }

    private fun cargarImagen(img_perfil: String)
    {
        Glide.with(this)
            .load(img_perfil)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(mBinding.ivImg)
    }

    private fun AddNewUser()
    {
        with(mBinding) {
            val Username = etUsername.text.toString()
            val Password = etPassword.text.toString()
            val Password2 = etPassword2.text.toString()
            val pais = tiPais.selectedItem.toString()
            val correo = etemail.text.toString()
            val genero = etgenero.text.toString()
            val imagen = etImg.text.toString()

            RegisterUser(Username, Password,Password2, pais, correo, genero, imagen)
        }

    }



    private fun ComprobarCampos(Username: String, Password: String, Password2: String, pais: String, correo: String, genero: String): Boolean {
        if(Username.isEmpty() || Password.isEmpty() || Password2.isEmpty() || pais.isEmpty() || correo.isEmpty() || genero.isEmpty())
        {
            Toast.makeText(this, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show()
        }
        else
        {
            if(Password == Password2)
            {
                password_correcto = Password
                Toast.makeText(this, "Contraseñas correctas", Toast.LENGTH_SHORT).show()
                return true
            }
            else
            {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return false
    }

    private fun RegisterUser(Username: String, Password: String,Password2: String, pais: String, correo: String, genero: String, imagen: String)
    {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userService = retrofit.create(UsuarioService::class.java)

        lifecycleScope.launch {
            try {
                if (ComprobarCampos(Username, Password, Password2, pais, correo, genero)) {
                    val response = userService.registerUser(Usuario_nuevo(Username, Password, correo, genero, pais, imagen))
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            RegisterSuccesfull()
                        } else {
                            RegisterBad()
                        }
                    }
                }else{
                    RegisterBad()
                }
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
        }


    }


}