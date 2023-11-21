package com.example.mocklinkedin.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.R
import com.example.mocklinkedin.databinding.FragmentEditPostBinding
import com.example.mocklinkedin.dto.Geo
import com.example.mocklinkedin.viewmodel.PostViewModel
import com.example.mocklinkedin.util.AndroidUtils
import com.example.mocklinkedin.util.StringArg
import java.text.SimpleDateFormat
import java.util.Calendar

class EditPostFragment: Fragment() {

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
    ) : View {
        val binding = FragmentEditPostBinding.inflate(
            inflater,
            container,
            false
        )

        val arg1Value = requireArguments().getString(Intent.EXTRA_TEXT)
        binding.edit.setText(arg1Value)

        binding.ok.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val dateTimeString = dateFormat.format(calendar.time)
            viewModel.changeContent(binding.edit.text.toString())
            val locationManager =requireContext().getSystemService(Context.LOCATION_SERVICE,) as LocationManager
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
                    viewModel.save(Geo(latitude, longitude) as Location, dateTimeString)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Geolocation is unavailable",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
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