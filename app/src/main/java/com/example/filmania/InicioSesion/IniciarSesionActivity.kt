package com.example.filmania.InicioSesion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmania.MainActivity
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



    }

    private fun LogIn() {
        val Username = mBinding.tilUsername.editText?.text.toString()
        val Password = mBinding.tilPassword.editText?.text.toString()

        if(Username.isEmpty() || Password.isEmpty()) {
            mBinding.tilUsername.error = "Campo vacio"
            mBinding.tilPassword.error = "Campo vacio"
        } else {
            val users = getUsers()
            val user = users.find { it.Username.equals(Username) && it.Password.equals(Password) }

            if (user != null) {
                saveUserId(user)
                if (getIsFirstTime(user.id)) {
                    saveIsFirstTime(false, user.id)
                    navigateToGeneroFragment()
                } else {
                    navigateToMainActivity()
                }
            } else {
                mBinding.tilUsername.error = "Usuario o contraseña incorrectos"
                mBinding.tilPassword.error = "Usuario o contraseña incorrectos"
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


    private fun saveIsFirstTime(isFirstTime: Boolean, userId: Int) {
        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isFirstTime_$userId", isFirstTime)
        editor.apply()
    }

    private fun getIsFirstTime(userId: Int): Boolean {
        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isFirstTime_$userId", true)
    }

    private fun saveUserId(user: Usuario ) {
        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("userId", user.id)
        editor.apply()
    }

    private fun getUsers(): MutableList<Usuario> {

        val Usuarios = mutableListOf<Usuario>()

        Usuarios.add(Usuario(1, "unfago", "Unai2004", "unaifagomail.com", "H","España", "https://i.pinimg.com/originals/50/68/b9/5068b980cf47495a6bda3aa70e22f982.jpg"))
        Usuarios.add(Usuario(2, "unfage", "Unai2004", "unaifagomail.com", "H","España", "https://i.pinimg.com/originals/50/68/b9/5068b980cf47495a6bda3aa70e22f982.jpg"))

        return Usuarios

    }


}