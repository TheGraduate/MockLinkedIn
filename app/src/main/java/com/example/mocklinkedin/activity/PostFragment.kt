package com.example.mocklinkedin.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mocklinkedin.adapter.OnInteractionListener
import com.example.mocklinkedin.post.Post
import com.example.mocklinkedin.adapter.PostViewHolder
import com.example.mocklinkedin.R
import com.example.mocklinkedin.databinding.CardPostBinding
import com.example.mocklinkedin.viewmodel.PostViewModel
import com.example.mocklinkedin.databinding.FragmentPostBinding
import com.google.android.material.button.MaterialButton

class PostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    val args by navArgs<PostFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        val viewHolder = PostViewHolder(binding.cardPost, object : OnInteractionListener {


            override fun onEdit(post: Post) {

                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }


                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
        })

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == args.postId.toLong() } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(post)
        }

        //val activity = activity as? AppActivity
        activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.GONE
        activity?.findViewById<ImageView>(R.id.newpost)?.visibility = View.GONE

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

       /* val deleteButton = view?.findViewById<Button>(R.id.remove)
        deleteButton?.setOnClickListener {
            activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.VISIBLE
            activity?.findViewById<ImageView>(R.id.newpost)?.visibility = View.VISIBLE
            Toast.makeText(requireContext(),"111",Toast.LENGTH_SHORT).show()
            //findNavController().navigate(R.id.action_postFragment_to_feedFragment)
        }*/

        return binding.root
    }


     /*fun navigateToProfileFragment() {
        if (activity is AppActivity) {
            (activity as AppActivity).navigateToProfileFragment()
        }
        activity?.findViewById<ImageView>(R.id.profile)?.setOnClickListener {

        }
    }*/


/* companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/

}
