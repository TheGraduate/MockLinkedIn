package com.example.mocklinkedin.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.R
import com.example.mocklinkedin.databinding.FragmentNewPostBinding
import com.example.mocklinkedin.util.AndroidUtils
import com.example.mocklinkedin.util.StringArg
import com.example.mocklinkedin.viewmodel.PostViewModel
import com.google.android.material.tabs.TabLayout


class NewPostFragment : Fragment()  {
    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )

        binding.ok.setOnClickListener {
            viewModel.changeContent(binding.postEditText.text.toString())
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(false)
            val activity = activity as? AppActivity
            activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.VISIBLE
            activity?.findViewById<ImageView>(R.id.newpost)?.visibility = View.VISIBLE
            //activity?.findViewById<TabLayout>(R.id.tabLayout)?.visibility = TabLayout.VISIBLE
        }
        return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }*/

}
