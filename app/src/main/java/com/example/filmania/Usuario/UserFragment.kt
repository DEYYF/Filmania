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
import com.bumptech.glide.Glide
import com.example.filmania.Añadir_Libreria.adapter.adapterAdd_Media_Libreria
import com.example.filmania.FilmaniaApplication
import com.example.filmania.Libreria_Contenido.Contenido_LibreriaFragment
import com.example.filmania.PantallaPrincipal.LogActivity
import com.example.filmania.R
import com.example.filmania.Retrofit.Librerias.LibreriaService
import com.example.filmania.Retrofit.Usuario.UsuarioService
import com.example.filmania.Retrofit.Usuario.Usuario_Update
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
import com.example.filmania.common.utils.OnClickListener
import com.example.filmania.databinding.DialogChangePasswordBinding
import com.example.filmania.databinding.FragmentUserBinding
import kotlinx.coroutines.launch

class UserFragment : Fragment(), OnClickListener{

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
            borrarUsuario()
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
                Glide.with(requireContext()).load(mUser.imagen).into(mBinding.ivPerfil)
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

                        if (oldPassword == user.password) {
                            if (newPassword1 == newPassword2){
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
                            }else{
                                binding.tilPassword2.error = "Las contraseñas no coinciden"
                                binding.tilPassword1.error = "Las contraseñas no coinciden"
                                Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                            }

                        } else {
                            binding.tilPasswordantiguo.error = "Contraseña incorrecta"
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

        if (newPassword1 != newPassword2) {
            binding.tilPassword2.error = "Las contraseñas no coinciden"
            return false
        }

        // Validación adicional según tus requisitos

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

    override fun onCLickGenero(genero: Genero) {
        TODO("Not yet implemented")
    }

    override fun onClickPelicula(pelicula: Peliculas) {
        TODO("Not yet implemented")
    }

    override fun onLongClickPelicula(pelicula: Peliculas) {
        TODO("Not yet implemented")
    }

    override fun onClickSerie(serie: Series) {
        TODO("Not yet implemented")
    }

    override fun onLongClickSerie(serie: Series) {
        TODO("Not yet implemented")
    }

    override fun onClickNoticia(noticias: Noticias) {
        TODO("Not yet implemented")
    }

    override fun onClickLibreria(Libreria: Libreria) {
        savelibreriaid(Libreria.id)
        changeContenido_libreria()
    }

    override fun onClickLibreriaDelete(Libreria: Libreria) {
        TODO("Not yet implemented")
    }

    override fun onClickBusqueda(busqueda: Busqueda) {
        TODO("Not yet implemented")
    }

    override fun onClickBusquedaAdd(busqueda: Busqueda) {
        TODO("Not yet implemented")
    }

    override fun onClickBusquedafav(busqueda: Busqueda) {
        TODO("Not yet implemented")
    }

    override fun onClickBusquedaVerMasTarde(busqueda: Busqueda) {
        TODO("Not yet implemented")
    }

    override fun onClickVistoAnteriormente(vistoAnteriormente: VistoAnteriormente) {
        TODO("Not yet implemented")
    }

    override fun onClickcontenido_libreria(contenidoLibreria: contenido_libreria) {
        TODO("Not yet implemented")
    }

    override fun onClickMedia(media: Media) {
        TODO("Not yet implemented")
    }

}
