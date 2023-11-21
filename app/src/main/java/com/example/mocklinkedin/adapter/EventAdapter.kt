package com.example.mocklinkedin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mocklinkedin.R
import com.example.mocklinkedin.dto.Event
//import com.example.mocklinkedin.util.CalculateParametrs
import com.example.mocklinkedin.databinding.EventCardBinding

interface OnInteractionListenerEvents {
    fun onLikeEvent(event: Event) {}
    fun onShareEvent(event: Event) {}
    fun onEditEvent(event: Event) {}
    fun onRemoveEvent(event: Event) {}
    fun onEvent(event: Event) {}
}
class EventsAdapter(
    private val onInteractionListenerEvents: OnInteractionListenerEvents ,
) : ListAdapter<Event, EventViewHolder>(EventDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding, onInteractionListenerEvents)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }
}

class EventViewHolder(
    private val binding: EventCardBinding,
    private val onInteractionListenerEvents: OnInteractionListenerEvents
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(event: Event) {
        binding.apply {
            author.text = event.author
            published.text = event.published.toString()
            content.text = event.content
            like.isChecked = event.likedByMe
            like.text = "${event.likes}"
            share.text = "${event.shares}"
            geo.text = event.geo.toString()
            //like.text = CalculateParametrs(event.likes)
            //share.text = CalculateParametrs(event.shares)
            //viewCount.text = CalculateParametrs(event.views)

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_for_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListenerEvents.onRemoveEvent(event)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListenerEvents.onEditEvent(event)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            like.setOnClickListener {
                onInteractionListenerEvents.onLikeEvent(event)
            }
            share.setOnClickListener {
                onInteractionListenerEvents.onShareEvent(event)
            }
            root.setOnClickListener {
                onInteractionListenerEvents.onEvent(event)
            }

            /* val url = "http://10.0.2.2:9999/avatars/{name}"
             Glide.with(binding.avatar)
                 .load(url)
                 .circleCrop()
                 //.placeholder(R.drawable.ic_loading_100dp)
                 //.error(R.drawable.ic_error_100dp)
                 .timeout(10_000)
                 .into(binding.avatar)*/
        }
    }
}

class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }
}
