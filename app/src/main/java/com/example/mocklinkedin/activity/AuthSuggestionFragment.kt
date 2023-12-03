package com.example.mocklinkedin.activity

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.R

class AuthSuggestionFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(R.layout.fragment_auth_suggestion)
            //.setTitle("Авторизация")
            .setPositiveButton(R.string.yes) { _, _ ->
                findNavController().navigate(R.id.action_homeFragment_to_registrationFragment)
                dialog?.dismiss()
            }
            .setNegativeButton(R.string.no) { _, _ ->
                dialog?.dismiss()
            }

        return builder.create()
    }
}