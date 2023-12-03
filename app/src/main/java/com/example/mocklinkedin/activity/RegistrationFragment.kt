package com.example.mocklinkedin.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.R
import com.example.mocklinkedin.databinding.FragmentRegistrationBinding
import com.example.mocklinkedin.viewmodel.RegistrationViewModel
import com.example.mocklinkedin.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar

class RegistrationFragment : Fragment() {
    private val viewModelReg: RegistrationViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private val viewModel: UserViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private lateinit var pickPhotoLauncher: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegistrationBinding.inflate(
            inflater,
            container,
            false
        )

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)

        val login = binding.editTextLogin
        val password = binding.editTextPassword
        val name = binding.editTextFirstName
        val avatar = viewModel.photo.value?.file?.path

        binding.registrationButton.setOnClickListener {
            if (login.text.isEmpty() || name.text.isEmpty() || password.text.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "One of fields is empty",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                actionBar?.setDisplayHomeAsUpEnabled(false)
                val activity = activity as? AppActivity
                activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.VISIBLE
                viewModelReg.registrationUser(login.text.toString(),password.text.toString(),name.text.toString(), avatar)
                findNavController().navigateUp()
            }
        }

        pickPhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
                viewModel.changePhoto(uri, uri?.toFile())
            } else if(result.resultCode == Activity.RESULT_CANCELED) {
                Snackbar.make(
                    binding.root,
                    "Error",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        binding.choseAvatarButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickPhotoLauncher.launch(intent)
            //viewModel.saveUser(login, firstName, password)
        }

        return binding.root
    }
}