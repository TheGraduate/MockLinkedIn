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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.databinding.FragmentFeedBinding

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
                viewModel.likeById(post.id)
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
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
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

       /* binding.root.setOnClickListener {
            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
        }*/

        /*binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        requireContext(),
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
            hideMessageMenu()
        }

        binding.cancel.setOnClickListener {
            hideMessageMenu()
            with(binding.content) {
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }*/

        return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.pager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)

        val fragmentList = listOf(BlankFragment1(), FeedFragment(), BlankFragment2())
        val adapter = ViewPagerAdapter(requireActivity(), fragmentList)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Fragment 1"
                1 -> tab.text = "Feed"
                2 -> tab.text = "Fragment 2"
            }
        }.attach()
    }*/

}