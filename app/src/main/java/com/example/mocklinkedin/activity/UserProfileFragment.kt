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
import com.example.mocklinkedin.R
import com.example.mocklinkedin.adapter.EventViewHolder
import com.example.mocklinkedin.adapter.JobViewHolder
import com.example.mocklinkedin.adapter.JobsAdapter
import com.example.mocklinkedin.adapter.OnInteractionListenerEvents
import com.example.mocklinkedin.adapter.OnInteractionListenerJobs
import com.example.mocklinkedin.databinding.FragmentEventBinding
import com.example.mocklinkedin.databinding.FragmentProfileBinding
import com.example.mocklinkedin.databinding.FragmentUserProfileBinding
import com.example.mocklinkedin.dto.Event
import com.example.mocklinkedin.dto.Job
import com.example.mocklinkedin.viewmodel.JobViewModel

/*
class UserProfileFragment: Fragment() {
    private var avatarImageView: ImageButton? = null

    private val viewModel: JobViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        val viewHolder = JobViewHolder(binding.eventCard, object : OnInteractionListenerJobs {

        })

        val adapter = JobsAdapter (object : OnInteractionListenerJobs {

        })

        binding.list.adapter = adapter
        */
/*viewModel.data.observe(viewLifecycleOwner) { jobs ->
            adapter.submitList(jobs.objects)
        }*//*


        return binding.root
    }
}*/
