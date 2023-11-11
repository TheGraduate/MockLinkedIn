package com.example.mocklinkedin.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.R
import com.example.mocklinkedin.databinding.FragmentEditEventBinding
import com.example.mocklinkedin.databinding.FragmentEditPostBinding
import com.example.mocklinkedin.util.AndroidUtils
import com.example.mocklinkedin.util.StringArg
import com.example.mocklinkedin.viewmodel.EventViewModel
import com.example.mocklinkedin.viewmodel.PostViewModel

class EditEventFragment: Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: EventViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val binding = FragmentEditEventBinding.inflate(
            inflater,
            container,
            false
        )

        val arg1Value = requireArguments().getString(Intent.EXTRA_TEXT)
        binding.edit.setText(arg1Value)

        binding.ok.setOnClickListener {
            viewModel.changeEventContent(binding.edit.text.toString())
            viewModel.saveEvent()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            val activity = activity as? AppActivity
            activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.VISIBLE
            activity?.findViewById<ImageView>(R.id.enterExit)?.visibility = View.VISIBLE
        }
        return binding.root
    }
}