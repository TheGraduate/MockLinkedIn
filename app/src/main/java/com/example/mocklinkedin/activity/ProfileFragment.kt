package com.example.mocklinkedin.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mocklinkedin.adapter.JobsAdapter
import com.example.mocklinkedin.adapter.OnInteractionListenerJobs
import com.example.mocklinkedin.databinding.FragmentProfileBinding
import com.example.mocklinkedin.dto.Job
import com.example.mocklinkedin.viewmodel.JobViewModel

class ProfileFragment : Fragment() {

    private var avatarImageView: ImageButton? = null

    private val viewModel: JobViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

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

        val adapter = JobsAdapter (object : OnInteractionListenerJobs {
            override fun onRemoveJob(job: Job) {
                viewModel.removeById(job.id)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { jobs ->
            adapter.submitList(jobs.objects)
        }



        avatarImageView = binding.avatarImageView
        val nameEditText = binding.NameEditText
        val loginEditText = binding.loginEditText

        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        nameEditText.setText(sharedPref.getString("nameEditText", ""))
        loginEditText.setText(sharedPref.getString("loginEditText", ""))

        avatarImageView?.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            someActivityResultLauncher.launch(intent)
        }

        binding.saveChangesButton.setOnClickListener {
            editor.putString("loginEditText", loginEditText.text.toString())
            editor.putString("nameEditText", nameEditText.text.toString())
            editor.apply()
        }

        binding.newJobButton.setOnClickListener {
            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(false)
            val action = ProfileFragmentDirections.actionProfileFragmentToNewJobFragment()
            requireParentFragment().findNavController().navigate(action)
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
