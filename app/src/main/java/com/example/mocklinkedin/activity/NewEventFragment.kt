package com.example.mocklinkedin.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.R
import com.example.mocklinkedin.databinding.FragmentNewPostBinding
import com.example.mocklinkedin.databinding.FragmentNewEventBinding
import com.example.mocklinkedin.util.AndroidUtils
import com.example.mocklinkedin.util.StringArg
import com.example.mocklinkedin.viewmodel.EventViewModel
import com.example.mocklinkedin.viewmodel.PostViewModel
import com.google.android.material.snackbar.Snackbar

class NewEventFragment: Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: EventViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private var fragmentBinding: FragmentNewEventBinding? = null

    private lateinit var pickPhotoLauncher: ActivityResultLauncher<Intent>
    private lateinit var takePhotoLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewEventBinding.inflate(
            inflater,
            container,
            false
        )

        fragmentBinding = binding
        arguments?.textArg
            ?.let(binding.edit::setText)
        binding.edit.requestFocus()

        takePhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
                viewModel.changeEventPhoto(uri, uri?.toFile())

            } else if(result.resultCode == Activity.RESULT_CANCELED) {
                Snackbar.make(
                    binding.root,
                    "Error",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        pickPhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
                viewModel.changeEventPhoto(uri, uri?.toFile())
            } else if(result.resultCode == Activity.RESULT_CANCELED) {
                Snackbar.make(
                    binding.root,
                    "Error",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        binding.pickPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickPhotoLauncher.launch(intent)
        }

        binding.takePhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePhotoLauncher.launch(intent)
        }

        binding.removePhoto.setOnClickListener {
            viewModel.changeEventPhoto(null, null)
        }

        viewModel.photo.observe(viewLifecycleOwner) {
            if (it.uri == null) {
                binding.photoContainer.visibility = View.GONE
                return@observe
            }

            binding.photoContainer.visibility = View.VISIBLE
            binding.photo.setImageURI(it.uri)
        }

        binding.ok.setOnClickListener {
            viewModel.changeEventContent(binding.edit.text.toString())
            viewModel.saveEvent()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(false)
            val activity = activity as? AppActivity
            activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.VISIBLE
            activity?.findViewById<ImageView>(R.id.enterExit)?.visibility = View.VISIBLE
        }
        return binding.root

    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

}
