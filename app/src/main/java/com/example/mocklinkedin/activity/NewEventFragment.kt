package com.example.mocklinkedin.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.R
import com.example.mocklinkedin.databinding.FragmentNewPostBinding
import com.example.mocklinkedin.databinding.FragmentNewEventBinding
import com.example.mocklinkedin.dto.Geo
import com.example.mocklinkedin.util.AndroidUtils
import com.example.mocklinkedin.util.StringArg
import com.example.mocklinkedin.viewmodel.EventViewModel
import com.example.mocklinkedin.viewmodel.PostViewModel
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar

class NewEventFragment: Fragment() {

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: EventViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private var fragmentBinding: FragmentNewEventBinding? = null

    private lateinit var pickPhotoLauncher: ActivityResultLauncher<Intent>
    private lateinit var takePhotoLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewEventBinding.inflate(
            inflater,
            container,
            false
        )

        fragmentBinding = binding
        arguments?.textArg
            ?.let(binding.edit::setText)
        binding.edit.requestFocus()

        takePhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
                viewModel.changeEventPhoto(uri, uri?.toFile())

            } else if(result.resultCode == Activity.RESULT_CANCELED) {
                Snackbar.make(
                    binding.root,
                    "Error",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        pickPhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
                viewModel.changeEventPhoto(uri, uri?.toFile())
            } else if(result.resultCode == Activity.RESULT_CANCELED) {
                Snackbar.make(
                    binding.root,
                    "Error",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        binding.pickPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickPhotoLauncher.launch(intent)
        }

        binding.takePhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePhotoLauncher.launch(intent)
        }

        binding.removePhoto.setOnClickListener {
            viewModel.changeEventPhoto(null, null)
        }

        viewModel.photo.observe(viewLifecycleOwner) {
            if (it.uri == null) {
                binding.photoContainer.visibility = View.GONE
                return@observe
            }

            binding.photoContainer.visibility = View.VISIBLE
            binding.photo.setImageURI(it.uri)
        }

        binding.ok.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val dateTimeString = dateFormat.format(calendar.time)
            viewModel.changeEventContent(binding.edit.text.toString())
            val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE,) as LocationManager
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    PERMISSION_REQUEST_CODE
                )
            } else {
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    viewModel.saveEvent(Geo(latitude, longitude) as Location, dateTimeString)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Geolocation is unavailable",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            //viewModel.changeContent(binding.edit.text.toString())
            //viewModel.save(location)
            //viewModel.saveEvent()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(false)
            val activity = activity as? AppActivity
            activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.VISIBLE
            activity?.findViewById<ImageView>(R.id.enterExit)?.visibility = View.VISIBLE
        }
        return binding.root

    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

}
