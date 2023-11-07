package com.example.mocklinkedin.activity

import android.os.Bundle
import android.view.View
import com.example.mocklinkedin.R
import android.widget.Toast
import com.example.mocklinkedin.adapter.OnInteractionListener
import com.example.mocklinkedin.post.Post
import com.example.mocklinkedin.adapter.PostsAdapter
import com.example.mocklinkedin.util.AndroidUtils
import com.example.mocklinkedin.viewmodel.PostViewModel
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
//import com.example.mocklinkedin.adapter.DemoCollectionAdapter
import com.example.mocklinkedin.databinding.FragmentFeedBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment/*{ requireActivity() }*/ // если поменять на requireActivity посты не будут сразу после написания
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
        //val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter (object : OnInteractionListener {
            override fun onEdit(post: Post) {
                //activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.GONE
                //activity?.findViewById<ImageView>(R.id.newpost)?.visibility = View.GONE
                //val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
                //actionBar?.setDisplayHomeAsUpEnabled(false)
                //val action = FeedFragmentDirections.actionFeedFragmentToEditPostFragment()//вылетает из за этой строчки
                //findNavController().navigateUp()

                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {

                viewModel.removeById(post.id)
            }

            override fun onPost(post: Post) {
                /*val activity2 = activity as? AppActivity
                activity2?.findViewById<ImageView>(R.id.profile)?.visibility = View.GONE
                activity2?.findViewById<ImageView>(R.id.newpost)?.visibility = View.GONE*/
                val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
                val action = FeedFragmentDirections.actionFeedFragmentToPostFragment(post.id.toInt())
                findNavController().navigate(action)
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

        /*fun hideMessageMenu() {
           binding.cancel.visibility = View.GONE
           binding.content.visibility = View.GONE
           binding.save.visibility = View.GONE
        }

        fun showMessageMenu() {
            binding.content.visibility = View.VISIBLE
            binding.save.visibility = View.VISIBLE
            binding.cancel.visibility = View.VISIBLE
        }*/

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0L) {
                return@observe
            }
            //if (activity is AppActivity) {
              /*  showMessageMenu()
                with(binding.content) {
                    requestFocus()
                    setText(post.content)
                }*/
           // }

                val sendPostText = Bundle()
                sendPostText.putString(Intent.EXTRA_TEXT, post.content)

            activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.GONE
            activity?.findViewById<ImageView>(R.id.newpost)?.visibility = View.GONE
            //val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            //actionBar?.setDisplayHomeAsUpEnabled(false)
            /*val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)*/

           /* val destinationFragment = EditPostFragment()
            destinationFragment.arguments = sendPostText*/
            findNavController().navigate(R.id.action_feedFragment_to_editPostFragment, sendPostText)

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