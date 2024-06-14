package com.example.filmania.InicioSesion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.filmania.FilmaniaApplication
import com.example.filmania.MainActivity
import com.example.filmania.Registro.RegistrarseActivity
import com.example.filmania.Retrofit.Usuario.UsuarioService
import com.example.filmania.SelectGenero.GeneroFragment
import com.example.filmania.common.Entyty.Usuario
import com.example.filmania.common.Entyty.Usuario_raw
import com.example.filmania.common.utils.Constantes
import com.example.filmania.databinding.ActivityIniciarSesionBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IniciarSesionActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityIniciarSesionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        cargarLogo()

        mBinding.btIniciarSesion.setOnClickListener {
            LogIn()
        }

        mBinding.btRegistro.setOnClickListener {
            navigateToRegistroActivity()
        }



        mBinding.tilPassword.editText?.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                if (!mBinding.tilUsername.editText?.text.isNullOrEmpty() &&
                    !mBinding.tilPassword.editText?.text.isNullOrEmpty()) {
                    LogIn()
                }
                true
            } else {
                false
            }
        }






    }


    private fun cargarLogo() {
        Glide.with(this)
            .load("https://s3.amazonaws.com/qreatech.com/Logo+Filmania+(1).jpg")
            .into(mBinding.ivBackground)
    }

    private fun LogIn() {
        val Username = mBinding.tilUsername.editText?.text.toString().trim()
        val Password = mBinding.tilPassword.editText?.text.toString().trim()

        if(Username.isEmpty() || Password.isEmpty()) {
            mBinding.tilUsername.error = "Campo vacio"
            mBinding.tilPassword.error = "Campo vacio"
        }else{
            val usuarioRaw = Usuario_raw(Username, Password)


            val userService = FilmaniaApplication.retrofit.create(UsuarioService::class.java)

            lifecycleScope.launch {
                try {
                    val response = userService.loginUser(usuarioRaw)
                    if(response.isSuccessful) {
                        val user = response.body()
                        if(user != null) {
                            saveUserId(user.id)
                            val isFirstTime = getIsFirstTime(user.id)
                            if(isFirstTime) {
                                navigateToGeneroFragment()
                                saveIsFirstTime(false, user.id)
                            }else{
                                navigateToMainActivity()
                            }
                        }else{
                            mBinding.tilUsername.error = "Usuario o contraseña incorrectos"
                            mBinding.tilPassword.error = "Usuario o contraseña incorrectos"
                        }
                }else{
                        mBinding.tilUsername.error = "Usuario o contraseña incorrectos"
                        mBinding.tilPassword.error = "Usuario o contraseña incorrectos"
                    }

                }catch (e: Exception) {
                    Log.e("IniciarSesionActivity", "Error: ${e.message}")
                }
            }

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

    private fun saveUserId(user: Int ) {
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("userId", user)
        editor.apply()
    }



}