package com.example.mocklinkedin.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.databinding.FragmentNewJobBinding
import com.example.mocklinkedin.util.AndroidUtils
import com.example.mocklinkedin.util.StringArg
import com.example.mocklinkedin.viewmodel.JobViewModel

class NewJobFragment: Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: JobViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private var fragmentBinding: FragmentNewJobBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewJobBinding.inflate(
            inflater,
            container,
            false
        )
        val companyName = binding.companyName.text.toString()
        val positionAtCompany = binding.positionAtCompany.text.toString()
        val dateStartOfWork = binding.dateStartOfWork.text.toString()
        val dateEndOfWork = binding.dateEndOfWork.text.toString()
        val companyLink = binding.companyLink.text.toString()

        fragmentBinding = binding
        arguments?.textArg
            ?.let(binding.companyName::setText)
        binding.companyName.requestFocus()

        binding.ok.setOnClickListener {
            viewModel.changeContent(
                companyName,
                positionAtCompany,
                dateStartOfWork,
                dateEndOfWork,
                companyLink
            )
            /*val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(false)
            val activity = activity as? AppActivity
            activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.VISIBLE
            activity?.findViewById<ImageView>(R.id.enterExit)?.visibility = View.VISIBLE
            activity?.findViewById<ImageView>(R.id.enterExit)?.visibility = View.GONE*/
            viewModel.saveJob(id, companyName, positionAtCompany, dateStartOfWork, dateEndOfWork, companyLink)
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        return binding.root

    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

}