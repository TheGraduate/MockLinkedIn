package com.example.mocklinkedin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mocklinkedin.R
import com.example.mocklinkedin.constants.Constants.Companion.BASE_URL
import com.example.mocklinkedin.databinding.CardPostBinding
import com.example.mocklinkedin.dto.Post
import com.example.mocklinkedin.view.load
import java.text.SimpleDateFormat
import java.util.Locale

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onPost(post: Post) {}
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    private val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = formatter.format(post.published)
            content.text = post.content
            like.isChecked = post.likedByMe
            //like.text = "${post.likes}"
            //share.text = "${post.shares}"
            //like.text = post.likes?.let { CalculateParametrs(it) }
            //share.text = post.shares?.let { CalculateParametrs(it) }
            //viewCount.text = post.views?.let { CalculateParametrs(it) }
            //avatar.loadCircleCrop("BASE_URL${post.authorAvatar}")
            "${post.coords?.latitude.toString()} : ${post.coords?.longitude.toString()}".also { geo.text = it }

            if (post.attachment == null) {
                attachmentImage.visibility = View.GONE
            } else {
                attachmentImage.visibility = View.VISIBLE
                post.attachment.let { attachmentImage.load(BASE_URL + it.url) }
            }

            Glide.with(binding.avatar)
                .load(post.authorAvatar)
                .circleCrop()
                .timeout(30_000)
                .into(binding.avatar)

            menu.visibility = if (post.ownedByMe) View.VISIBLE else View.INVISIBLE

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_for_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            like.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            share.setOnClickListener {
                onInteractionListener.onShare(post)
            }
            root.setOnClickListener {
                onInteractionListener.onPost(post)
            }

           /* val url = "http://10.0.2.2:9999/avatars/{username}"
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

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

