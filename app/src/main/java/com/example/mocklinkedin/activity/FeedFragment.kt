package com.example.mocklinkedin.activity

import android.os.Bundle
import android.view.View
import com.example.mocklinkedin.R
import com.example.mocklinkedin.adapter.OnInteractionListener
import com.example.mocklinkedin.dto.Post
import com.example.mocklinkedin.adapter.PostsAdapter
import com.example.mocklinkedin.viewmodel.PostViewModel
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mocklinkedin.databinding.FragmentFeedBinding
import com.google.android.material.snackbar.Snackbar

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = PostsAdapter (object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                if (post.likedByMe) {
                    viewModel.unlikeById(post.id)
                } else {
                    viewModel.likeById(post.id)
                }
            }

            override fun onRemove(post: Post) {

                viewModel.removeById(post.id)
            }

            override fun onPost(post: Post) {
                val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
                val action = HomeFragmentDirections.actionHomeFragmentToPostFragment(post.id.toInt())
                requireParentFragment().findNavController().navigate(action)
                actionBar?.setDisplayHomeAsUpEnabled(true)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
        })

        binding.list.adapter = adapter
        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state.loading
            binding.swiperefresh.isRefreshing = state.refreshing
            if (state.error) {
                Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading) { viewModel.loadPosts() }
                    .show()
            }
        }

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (positionStart == 0){
                    binding.list.smoothScrollToPosition(0)
                }
            }
        })

        viewModel.data.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.objects)
            binding.emptyText.isVisible = state.empty
        }

        viewModel.newerCount.observe(viewLifecycleOwner) { state ->
            binding.showPosts.isVisible = state > 0
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.refreshPosts()
        }

        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0L) {
                return@observe
            }

            val sendPostText = Bundle()
            sendPostText.putString(Intent.EXTRA_TEXT, post.content)
            activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.GONE
            activity?.findViewById<ImageView>(R.id.enterExit)?.visibility = View.GONE
            requireParentFragment().findNavController().navigate(R.id.action_homeFragment_to_editPostFragment, sendPostText)
        }

        binding.floatingNewPostButton.setOnClickListener {
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