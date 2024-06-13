import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmania.FilmaniaApplication
import com.example.filmania.Libreria_Contenido.Contenido_LibreriaFragment
import com.example.filmania.PantallaPrincipal.LogActivity
import com.example.filmania.R
import com.example.filmania.Retrofit.Librerias.LibreriaService
import com.example.filmania.Retrofit.Usuario.UsuarioService
import com.example.filmania.Retrofit.Usuario.Usuario_config
import com.example.filmania.Retrofit.VistoAnteriormente.VistoAnteriormente
import com.example.filmania.Usuario.adapter.useradapter
import com.example.filmania.common.Entyty.Busqueda
import com.example.filmania.common.Entyty.Genero
import com.example.filmania.common.Entyty.Libreria
import com.example.filmania.common.Entyty.Media
import com.example.filmania.common.Entyty.Noticias
import com.example.filmania.common.Entyty.Peliculas
import com.example.filmania.common.Entyty.Series
import com.example.filmania.common.Entyty.Usuario
import com.example.filmania.common.Entyty.contenido_libreria
import com.example.filmania.common.utils.Listeners.OnClickListener
import com.example.filmania.common.utils.Listeners.OnClickListenerLibreria
import com.example.filmania.databinding.DialogChangePasswordBinding
import com.example.filmania.databinding.FragmentUserBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class UserFragment : Fragment(), OnClickListenerLibreria {

    private lateinit var mBinding: FragmentUserBinding
    private lateinit var mUser: Usuario
    private lateinit var binding: DialogChangePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUserBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cargarUsuario()
        setuprecyclerview()

        mBinding.btnEdit.setOnClickListener {
            activarModoEditar()
        }

        mBinding.btnCancel.setOnClickListener {
            desactivarModoEditar()
            cargarUsuario()
        }

        mBinding.btnConfirm.setOnClickListener {
            editarUser()
        }

        mBinding.btnDelete.setOnClickListener {
            showDeleteUserDialog()
        }

        mBinding.btnLogout.setOnClickListener {
            goToLogActivity()
        }

        mBinding.btnContrasena.setOnClickListener {
            showChangePasswordDialog()
        }

        mBinding.btnBack.setOnClickListener {
            goBack()
        }
    }


    private fun setuprecyclerview(){
        val adapter = useradapter(this)
        val librerialayoutManager = LinearLayoutManager(context)
        mBinding.rvLibrerias.layoutManager = librerialayoutManager
        mBinding.rvLibrerias.adapter = adapter

        cargarLibreria()
    }

    private fun activarModoEditar() {
        mBinding.etUsername.isEnabled = true
        mBinding.etEmail.isEnabled = true

        mBinding.btnConfirm.visibility = View.VISIBLE
        mBinding.btnCancel.visibility = View.VISIBLE
        mBinding.btnEdit.visibility = View.GONE
        mBinding.btnDelete.visibility = View.GONE
    }

    private fun desactivarModoEditar() {
        mBinding.etUsername.isEnabled = false
        mBinding.etEmail.isEnabled = false

        mBinding.btnConfirm.visibility = View.GONE
        mBinding.btnCancel.visibility = View.GONE
        mBinding.btnEdit.visibility = View.VISIBLE
        mBinding.btnDelete.visibility = View.VISIBLE
    }

    private fun cargarLibreria() {
        val libreriaService = FilmaniaApplication.retrofit.create(LibreriaService::class.java)

        lifecycleScope.launch {
            val response = libreriaService.getLibreriaUser(getUserId())
            if (response.isSuccessful) {
                val libreria = response.body()
                val adapter = mBinding.rvLibrerias.adapter as useradapter
                adapter.submitList(libreria)
            } else {
                Toast.makeText(requireContext(), "Error al cargar la libreria", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun editarUser() {
        val usuarioService = FilmaniaApplication.retrofit.create(UsuarioService::class.java)

        lifecycleScope.launch {
            try {
                val updatedUser = Usuario_config(
                    mBinding.etUsername.text.toString(),
                    mUser.password,
                    mBinding.etEmail.text.toString(),
                    mUser.genero,
                    mUser.pais,
                    mUser.imagen
                )
                val response = usuarioService.updateUser(getUserId(), updatedUser)
                if (response.isSuccessful) {
                    desactivarModoEditar()
                    cargarUsuario()
                }
            } catch (e: Exception) {
                Log.e("UserFragment", e.message.toString())
            }
        }
    }

    private fun cargarUsuario() {
        val usuarioService = FilmaniaApplication.retrofit.create(UsuarioService::class.java)

        lifecycleScope.launch {
            try {
                val response = usuarioService.getUser(getUserId())
                mUser = response.body()!!
                mBinding.etUsername.setText(mUser.usuarios)
                mBinding.etEmail.setText(mUser.email)
                if (mUser.imagen.contains(".jpg") || mUser.imagen.contains(".png") || mUser.imagen.contains(".jpeg")
                    || mUser.imagen.contains(".gif") || mUser.imagen.contains(".bmp") || mUser.imagen.contains(".webp")) {
                    Picasso.get().load(mUser.imagen).into(mBinding.ivPerfil)
                } else {
                    Picasso.get().load(R.drawable.ic_account_circle_24).into(mBinding.ivPerfil)
                }
            } catch (e: Exception) {
                Log.e("UserFragment", e.message.toString())
            }
        }
    }

    private fun borrarUsuario() {
        val usuarioService = FilmaniaApplication.retrofit.create(UsuarioService::class.java)

        lifecycleScope.launch {
            try {
                val response = usuarioService.deleteUser(getUserId())
                if (response.isSuccessful) {
                    goToLogActivity()
                }
            } catch (e: Exception) {
                Log.e("UserFragment", e.message.toString())
            }
        }
    }

    private fun goToLogActivity() {
        val intent = Intent(requireContext(), LogActivity::class.java)
        startActivity(intent)
    }

    private fun getUserId(): Long {
        val sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("userId", 0).toLong()
    }

    private fun showDeleteUserDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Borrar Usuario")
            .setMessage(R.string.Delete_dialog)
            .setPositiveButton("Confirmar") { dialog, _ ->
                borrarUsuario()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun showChangePasswordDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_change_password, null)
        binding = DialogChangePasswordBinding.bind(dialogView)

        AlertDialog.Builder(requireContext())
            .setTitle("Change Password")
            .setView(dialogView)
            .setPositiveButton("Confirm") { dialog, _ ->
                val oldPassword = binding.etPasswordantiguo.text.toString()
                val newPassword1 = binding.etPassword1.text.toString()
                val newPassword2 = binding.etPassword2.text.toString()

                if (validatePasswords(oldPassword, newPassword1, newPassword2)) {
                    val usuarioService = FilmaniaApplication.retrofit.create(UsuarioService::class.java)

                    lifecycleScope.launch {
                        val response = usuarioService.getUser(getUserId())
                        val user = response.body()!!

                        if (oldPassword == user.password && newPassword1 == newPassword2) {
                                val updatedUser = Usuario_config(
                                    mBinding.etUsername.text.toString(),
                                    newPassword1,
                                    mBinding.etEmail.text.toString(),
                                    user.genero,
                                    user.pais,
                                    user.imagen
                                )
                                val response = usuarioService.updateUser(getUserId(), updatedUser)
                                if (response.isSuccessful) {
                                    Toast.makeText(requireContext(), "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }else{
                                    Toast.makeText(requireContext(), "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show()
                                }


                        } else {
                            Toast.makeText(requireContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun validatePasswords(oldPassword: String, newPassword1: String, newPassword2: String): Boolean {
        if (oldPassword.isEmpty()) {
            binding.tilPasswordantiguo.error = "Introduce tu contraseña actual"
            return false
        }

        if (newPassword1.isEmpty()) {
            binding.tilPassword1.error = "Introduce la nueva contraseña"
            return false
        }

        if (newPassword2.isEmpty()) {
            binding.tilPassword2.error = "Repite la nueva contraseña"
            return false
        }

        return true
    }


    private fun savelibreriaid(id: Long) {
        val sharedPref = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putLong("libreriaId", id)
        editor.apply()
    }

    private fun changeContenido_libreria() {
        val fragment = Contenido_LibreriaFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.hostFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun goBack() {
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.popBackStack()
    }


    override fun onClickLibreria(Libreria: Libreria) {
        savelibreriaid(Libreria.id)
        changeContenido_libreria()
    }

    override fun onClickLibreriaDelete(Libreria: Libreria) {
        TODO("Not yet implemented")
    }


}
