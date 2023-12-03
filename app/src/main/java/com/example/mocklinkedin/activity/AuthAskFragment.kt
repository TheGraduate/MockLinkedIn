package com.example.mocklinkedin.activity

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.DialogFragment
import com.example.mocklinkedin.R
import com.example.mocklinkedin.auth.AppAuth

class AuthAskFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(R.layout.fragment_auth_ask)
            //.setTitle("Вы уверены?")
            .setPositiveButton(R.string.yes) { _, _ ->
                AppAuth.getInstance().removeAuth()
                dialog?.dismiss()
                invalidateOptionsMenu(requireActivity())
            }
            .setNegativeButton(R.string.no) { _, _ ->
                dialog?.dismiss()

            }
        return builder.create()
    }
}