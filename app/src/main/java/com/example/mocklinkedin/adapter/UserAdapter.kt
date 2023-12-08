package com.example.mocklinkedin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mocklinkedin.databinding.UserCardBinding
import com.example.mocklinkedin.dto.Post
import com.example.mocklinkedin.dto.User

interface OnInteractionListenerUsers {
    fun onUser(user: User) {}
}

class UsersAdapter(
    private val onInteractionListenerUsers: OnInteractionListenerUsers ,
) : ListAdapter<User, UserViewHolder>(UserDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, onInteractionListenerUsers)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}

class UserViewHolder(
    private val binding: UserCardBinding,
    private val onInteractionListenerUsers: OnInteractionListenerUsers
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.apply {

            login.text = user.login
            //name.text = user.name

            Glide.with(binding.userAvatar)
                .load(user.avatar)
                .circleCrop()
                .timeout(30_000)
                .into(binding.userAvatar)
           /* menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_for_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListenerJobs.onRemoveJob(job)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }*/

            //root.setOnClickListener {
                //OnInteractionListenerUsers.onUser(user)
            //}
        }
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}
