package com.example.mocklinkedin.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mocklinkedin.R
import com.example.mocklinkedin.adapter.EventViewHolder
import com.example.mocklinkedin.adapter.OnInteractionListenerEvents
import com.example.mocklinkedin.databinding.FragmentEventBinding
import com.example.mocklinkedin.dto.Event
import com.example.mocklinkedin.viewmodel.EventViewModel

class EventFragment : Fragment() {

    private val viewModel: EventViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    val args by navArgs<PostFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEventBinding.inflate(inflater, container, false)
        val viewHolder = EventViewHolder(binding.eventCard, object : OnInteractionListenerEvents {
            override fun onEditEvent(event: Event) {
                viewModel.editEvent(event)
            }
            override fun onLikeEvent(event: Event) {
                viewModel.likeEventById(event.id)
            }
            override fun onRemoveEvent(event: Event) {
                viewModel.removeEventById(event.id)
            }
            override fun onShareEvent(event: Event) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, event.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
        })

        viewModel.data.observe(viewLifecycleOwner) { events ->
            val event = events.objects.find { it.id == args.postId.toLong() } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(event)
        }

        activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.GONE
        //activity?.findViewById<ImageView>(R.id.log_in)?.visibility = View.GONE

        viewModel.edited.observe(viewLifecycleOwner) {
            if (it.id == 0L) {
                return@observe
            }

            val sendPostText = Bundle()
            sendPostText.putString(Intent.EXTRA_TEXT, it.content)
            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(false)
            findNavController().navigate(R.id.action_postFragment_to_editPostFragment, sendPostText)
        }
        return binding.root
    }
}
