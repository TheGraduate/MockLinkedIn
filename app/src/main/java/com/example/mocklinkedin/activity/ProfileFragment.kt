package com.example.mocklinkedin.activity

/*import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mocklinkedin.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentProfileBinding.inflate(inflater,
            container,
            false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {

            val newUserName = binding.editTextUserName.text.toString()


        }
    }
}*/

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mocklinkedin.R
import com.example.mocklinkedin.databinding.FragmentNewPostBinding
import com.example.mocklinkedin.databinding.FragmentProfileBinding
import com.example.mocklinkedin.viewmodel.PostViewModel
import com.example.mocklinkedin.viewmodel.UserViewModel

class ProfileFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private var avatarImageView: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(
            inflater,
            container,
            false
        )

        avatarImageView = binding.avatarImageView
        val changeAvatarButton = binding.changeAvatarButton
        val firstNameEditText = binding.firstNameEditText
        val lastNameEditText = binding.lastNameEditText
        val usernameEditText = binding.usernameEditText
        val birthDateEditText = binding.birthDateEditText

        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        firstNameEditText.setText(sharedPref.getString("firstNameEditText", ""))
        lastNameEditText.setText(sharedPref.getString("lastNameEditText", ""))
        usernameEditText.setText(sharedPref.getString("usernameEditText", ""))
        birthDateEditText.setText(sharedPref.getString("birthDateEditText", ""))

        changeAvatarButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            someActivityResultLauncher.launch(intent)
        }

        binding.saveChangesButton.setOnClickListener {
            editor.putString("usernameEditText", usernameEditText.text.toString())
            editor.putString("firstNameEditText", firstNameEditText.text.toString())
            editor.putString("lastNameEditText", lastNameEditText.text.toString())
            editor.putString("birthDateEditText", birthDateEditText.text.toString())
            editor.apply()
        }

        return binding.root
    }

    private val someActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri = data?.data

            if (selectedImageUri != null) {
                avatarImageView?.let {
                    Glide.with(this)
                        .load(selectedImageUri)
                        //.diskCacheStrategy(DiskCacheStrategy.NONE)
                        //.skipMemoryCache(true)
                        .into(it)
                }
            }
        }
    }
}
