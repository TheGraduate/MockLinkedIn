package com.example.mocklinkedin.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mocklinkedin.adapter.OnInteractionListener
import com.example.mocklinkedin.dto.Post
import com.example.mocklinkedin.adapter.PostViewHolder
import com.example.mocklinkedin.R
import com.example.mocklinkedin.viewmodel.PostViewModel
import com.example.mocklinkedin.databinding.FragmentPostBinding

class PostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    val args by navArgs<PostFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(inflater,
            container,
            false)
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
            val post = posts.objects.find { it.id == args.postId.toLong() } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(post)
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
