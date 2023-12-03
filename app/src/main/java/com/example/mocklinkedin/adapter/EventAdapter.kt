package com.example.mocklinkedin.adapter

//import com.example.mocklinkedin.util.CalculateParametrs
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mocklinkedin.R
import com.example.mocklinkedin.databinding.EventCardBinding
import com.example.mocklinkedin.dto.Event
import java.text.SimpleDateFormat
import java.util.Locale

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

    private val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
    fun bind(event: Event) {
        binding.apply {
            author.text = event.author
            published.text = formatter.format(event.published)
            content.text = event.content
            like.isChecked = event.likedByMe
            //like.text = "${event.likes}"
            //share.text = "${event.shares}"
            geo.text = event.coords.toString()
            //like.text = event.likes?.let { CalculateParametrs(it) }
            //share.text = event.shares?.let { CalculateParametrs(it) }
            //viewCount.text = event.views?.let { CalculateParametrs(it) }
            "${event.coords?.latitude.toString()} : ${event.coords?.longitude.toString()}".also { geo.text = it }

            Glide.with(binding.avatar)
                .load(event.authorAvatar)
                .circleCrop()
                .timeout(30_000)
                .into(binding.avatar)

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
