package com.example.filmania.Registro

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.filmania.FilmaniaApplication
import com.example.filmania.InicioSesion.IniciarSesionActivity
import com.example.filmania.R
import com.example.filmania.Registro.Adapter.CountrySpinnerAdapter
import com.example.filmania.Retrofit.Countrys.CountryService
import com.example.filmania.Retrofit.Usuario.UsuarioService
import com.example.filmania.Retrofit.Usuario.Usuario_Update
import com.example.filmania.common.Entyty.Country
import com.example.filmania.common.Entyty.Usuario
import com.example.filmania.common.Entyty.Usuario_nuevo
import com.example.filmania.common.utils.Constantes
import com.example.filmania.databinding.ActivityRegistrarseBinding
import com.squareup.picasso.Picasso
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

                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

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
        Picasso.get().load(img_perfil).into(mBinding.ivImg)
    }

    private fun AddNewUser() {
        with(mBinding) {
            val Username = etUsername.text.toString().trim()
            val Password = etPassword.text.toString().trim()
            val Password2 = etPassword2.text.toString().trim()
            val pais = tiPais.selectedItem.toString().trim()
            val correo = etemail.text.toString().trim()
            val genero = etgenero.text.toString().trim()
            val imagen = etImg.text.toString().trim()

            RegisterUser(Username, Password, Password2, pais, correo, genero, imagen)
        }
    }






    private fun ComprobarCampos(Username: String, Password: String, Password2: String, pais: String, correo: String, genero: String, imagen: String): Boolean {
        if (Username.isEmpty() || Password.isEmpty() || Password2.isEmpty() || pais.isEmpty() || correo.isEmpty() || genero.isEmpty() || imagen.isEmpty()) {
            Toast.makeText(this, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show()
            return false
        } else {
            if (Password != Password2) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return false
            }
            if (!isValidPassword(Password)) {
                Toast.makeText(this, "La contraseña debe tener al menos una letra mayúscula, un carácter especial y un número", Toast.LENGTH_SHORT).show()
                return false
            }
            if (!isValidEmail(correo)) {
                Toast.makeText(this, "Formato de correo electrónico inválido", Toast.LENGTH_SHORT).show()
                return false
            }
            if (!isValidImageUrl(imagen)) {
                Toast.makeText(this, "URL de imagen inválida", Toast.LENGTH_SHORT).show()
                return false
            }
            password_correcto = Password
            Toast.makeText(this, "Todos los campos son válidos", Toast.LENGTH_SHORT).show()
            return true
        }
    }


    private fun RegisterUser(Username: String, Password: String,Password2: String, pais: String, correo: String, genero: String, imagen: String)
    {

        val userService = FilmaniaApplication.retrofit.create(UsuarioService::class.java)

        lifecycleScope.launch {
            try {
                if (ComprobarCampos(Username, Password, Password2, pais, correo, genero, imagen)) {
                    val response = userService.registerUser(Usuario_nuevo(Username, Password, correo, genero, pais, imagen))
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            RegisterSuccesfull()
                        } else {
                            RegisterBad()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
        }


    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }



    private fun isValidImageUrl(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches() && url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".png") || url.endsWith(".gif") || url.endsWith(".bmp") || url.endsWith(".webp") || url.endsWith(".svg")
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"
        return password.matches(passwordPattern.toRegex())
    }




}