package com.example.mocklinkedin.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.R
import com.example.mocklinkedin.adapter.EventsAdapter
import com.example.mocklinkedin.adapter.OnInteractionListener
import com.example.mocklinkedin.adapter.OnInteractionListenerEvents
import com.example.mocklinkedin.adapter.PostsAdapter
import com.example.mocklinkedin.databinding.FragmentEventsBinding
import com.example.mocklinkedin.databinding.FragmentFeedBinding
import com.example.mocklinkedin.dto.Event
import com.example.mocklinkedin.dto.Post
import com.example.mocklinkedin.viewmodel.EventViewModel
import com.example.mocklinkedin.viewmodel.PostViewModel


class EventsFragment : Fragment() {

    private val viewModel: EventViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEventsBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = EventsAdapter (object : OnInteractionListenerEvents {
            override fun onEditEvent(event: Event) {
                viewModel.editEvent(event)
            }

            override fun onLikeEvent(event: Event) {
                viewModel.likeEventById(event.id)
            }

            override fun onRemoveEvent(event: Event) {

                viewModel.removeEventById(event.id)
            }

            override fun onEvent(event: Event) {
                val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
                val action = HomeFragmentDirections.actionHomeFragmentToPostFragment(event.id.toInt())
                requireParentFragment().findNavController().navigate(action)
                actionBar?.setDisplayHomeAsUpEnabled(true)
            }

            override fun onShareEvent(event: Event) {
                viewModel.shareEventById(event.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, event.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { events ->
            adapter.submitList(events)
        }

        viewModel.edited.observe(viewLifecycleOwner) { event ->
            if (event.id == 0L) {
                return@observe
            }

            val sendPostText = Bundle()
            sendPostText.putString(Intent.EXTRA_TEXT, event.content)
            activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.GONE
            activity?.findViewById<ImageView>(R.id.enterExit)?.visibility = View.GONE
            requireParentFragment().findNavController().navigate(R.id.action_homeFragment_to_editPostFragment, sendPostText)
        }

        binding.floatingNewEventButton.setOnClickListener {
            //requireParentFragment().findNavController().navigate(R.id.action_homeFragment_to_newPostFragment)
            val action = HomeFragmentDirections.actionHomeFragmentToNewPostFragment()
            requireParentFragment().findNavController().navigate(action)
            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.GONE
            activity?.findViewById<ImageView>(R.id.enterExit)?.visibility = View.GONE
        }

        return binding.root
    }
}