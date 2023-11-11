package com.example.mocklinkedin.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.databinding.FragmentRegistrationBinding
import com.example.mocklinkedin.dto.User
import com.example.mocklinkedin.viewmodel.UserViewModel
class RegistrationFragment : Fragment() {
    private val viewModel: UserViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
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

        binding.registrationButton.setOnClickListener {
            findNavController().navigateUp()
        }

        val username = binding.editTextUsername.text.toString()
        val firstName = binding.editTextFirstName.text.toString()
        val lastName = binding.editTextLastName.text.toString()
        val password = binding.editTextPassword.text.toString()


        binding.registrationButton.setOnClickListener {
            viewModel.setUserData(username, firstName, lastName, password)
            viewModel.saveUser()
            findNavController().navigateUp()
        }
        return binding.root
    }
}