package com.example.mocklinkedin.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mocklinkedin.adapter.OnInteractionListenerUsers
import com.example.mocklinkedin.adapter.UsersAdapter
import com.example.mocklinkedin.databinding.FragmentUsersBinding
import com.example.mocklinkedin.viewmodel.UserViewModel

class UsersListFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUsersBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = UsersAdapter (object : OnInteractionListenerUsers {

        })

       /* binding.list.adapter = adapter
        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state.loading
            binding.swiperefresh.isRefreshing = state.refreshing
            if (state.error) {
                Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading) { viewModel.loadPosts() }
                    .show()
            }
        }*/
        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { users ->
            adapter.submitList(users.objects)
        }

        viewModel.edited.observe(viewLifecycleOwner) { user ->
            if (user.id == 0L) {
                return@observe
            }
        }

        return binding.root
    }
}