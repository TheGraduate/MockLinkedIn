package com.example.mocklinkedin.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.R
import com.example.mocklinkedin.auth.AppAuth
import com.example.mocklinkedin.databinding.FragmentLoginBinding
import com.example.mocklinkedin.viewmodel.RegistrationViewModel
import com.example.mocklinkedin.viewmodel.UserViewModel

class LoginFragment : Fragment() {
    private val viewModelReg: RegistrationViewModel by viewModels()

    private val viewModel: UserViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.enter_exit_menu, menu)
        setHasOptionsMenu(false)
        setMenuVisibility(false)
        super.onCreateOptionsMenu(menu, inflater)
    }*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //setHasOptionsMenu(false) // Скрыть меню в этом фрагменте

        val binding = FragmentLoginBinding.inflate(
            inflater,
            container,
            false
        )

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        val login = binding.editTextLogin.text.toString()
        val password = binding.editTextPassword.text.toString()


        binding.loginButton.setOnClickListener {
            //if(viewModel.authenticateUser(login,password)) {
                viewModelReg.updateUser(login, password)
                actionBar?.setDisplayHomeAsUpEnabled(false)
                val activity = activity as? AppActivity
                activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.VISIBLE
                //activity?.findViewById<ImageView>(R.id.log_out)?.visibility = View.VISIBLE
                findNavController().navigateUp()
                //activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.VISIBLE
                //activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.VISIBLE
            //} else {
                //Toast.makeText(requireContext(), R.string.existUser, Toast.LENGTH_SHORT).show()
            //}
        }

        binding.suggestRegistration.setOnClickListener {
            AppAuth.getInstance().setAuth(5, "x-token")
            val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
            requireParentFragment().findNavController().navigate(action)
        }

        return binding.root
    }
}