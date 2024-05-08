package com.example.filmania.Registro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.filmania.InicioSesion.IniciarSesionActivity
import com.example.filmania.R
import com.example.filmania.Registro.Adapter.CountrySpinnerAdapter
import com.example.filmania.Retrofit.Countrys.CountryService
import com.example.filmania.common.Entyty.Country
import com.example.filmania.common.Entyty.Usuario
import com.example.filmania.common.Entyty.UsuarioNuevo
import com.example.filmania.databinding.ActivityRegistrarseBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistrarseActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegistrarseBinding

    private lateinit var password_correcto: String

    private var pais: MutableList<Country> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

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
                    Log.e("Paises", pais.toString())
                    val adapter = CountrySpinnerAdapter(this@RegistrarseActivity, pais)
                    mBinding.tiPais.adapter = adapter

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
        val Username = mBinding.etUsername.toString()
        val Password = mBinding.etPassword.toString()
        val Password2 = mBinding.etPassword2.toString()
        val selectedpais = mBinding.tiPais.selectedItem as Country
        val pais = selectedpais.name
        val correo = mBinding.etemail.toString()
        val genero = if (mBinding.cbHombre.isChecked){
            "Hombre"
        } else if (mBinding.cbMujer.isChecked){
            "Mujer"
        } else {
            "Otro"
        }

        ComprobarCampos(Username, Password, Password2, pais, correo, genero)
        RegisterUser(Username, Password, pais, correo, genero)





    }



    private fun ComprobarCampos(Username: String, Password: String, Password2: String, pais: String, correo: String, genero: String) {
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
            }
            else
            {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun RegisterUser(Username: String, Password: String, pais: String, correo: String, genero: String)
    {
        // Aquí se debería de hacer la conexión a la base de datos para registrar al usuario
        // Si la conexión es exitosa, llamar a RegisterSuccesfull()
        // Si la conexión falla, llamar a RegisterBad()

        val Username = UsuarioNuevo(Username, password_correcto, correo, genero, pais, mBinding.etImg.toString())
    }


}