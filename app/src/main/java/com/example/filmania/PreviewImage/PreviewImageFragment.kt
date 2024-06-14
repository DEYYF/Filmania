import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.filmania.FilmaniaApplication
import com.example.filmania.Retrofit.Usuario.UsuarioService
import com.example.filmania.databinding.FragmentPreviewImageBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class PreviewImageFragment : Fragment() {

        private lateinit var binding: FragmentPreviewImageBinding

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentPreviewImageBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


            cargarImage()

            binding.closePreviewImage.setOnClickListener {
                goBack()
            }
        }


        private fun cargarImage() {
            val usuarioService = FilmaniaApplication.retrofit.create(UsuarioService::class.java)

            lifecycleScope.launch {
                val response = usuarioService.getUser(getUserId())
                if (response.isSuccessful){
                    val url = response.body()?.imagen
                    Picasso.get().load(url).into(binding.previewImage)
                }else{
                    Toast.makeText(requireContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show()
                }
            }
        }


        private fun getUserId(): Long {
            val sharedPref = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
            return sharedPref.getInt("userId", 0).toLong()
        }

        private fun goBack() {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }
}