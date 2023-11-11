package com.example.mocklinkedin.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.R
import com.example.mocklinkedin.databinding.FragmentHomeBinding
import com.example.mocklinkedin.databinding.FragmentLoginBinding
import com.example.mocklinkedin.viewmodel.UserViewModel

class LoginFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = FragmentLoginBinding.inflate(
            inflater,
            container,
            false
        )

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        val username = binding.editTextLogin.text.toString()
        val password = binding.editTextPassword.text.toString()

        binding.loginButton.setOnClickListener {
            if(viewModel.UserExist(username, password)) {
                actionBar?.setDisplayHomeAsUpEnabled(false)
                val activity = activity as? AppActivity
                activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.VISIBLE
                activity?.findViewById<ImageView>(R.id.enterExit)?.visibility = View.VISIBLE
                findNavController().navigateUp()
                activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.VISIBLE
                activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(), R.string.existUser, Toast.LENGTH_SHORT).show()
            }
        }

        binding.suggestRegistration.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
            requireParentFragment().findNavController().navigate(action)
        }

        return binding.root
    }
}