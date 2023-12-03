package com.example.mocklinkedin.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import com.example.mocklinkedin.databinding.FragmentEditEventBinding
import com.example.mocklinkedin.dto.Geo
import com.example.mocklinkedin.util.AndroidUtils
import com.example.mocklinkedin.viewmodel.EventViewModel
import java.time.LocalDateTime
import java.time.ZoneId

class EditEventFragment: Fragment() {

    /*companion object {
        var Bundle.textArg: String? by StringArg
    }*/

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
            val currentDateTime = LocalDateTime.now()
            val milliseconds = currentDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

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
                    val geo = Geo(location.latitude, location.longitude)
                    viewModel.saveEvent(geo, milliseconds)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Geolocation is unavailable",
                        Toast.LENGTH_LONG
                    ).show()
                    val geo = Geo(0.0, 0.0)
                    viewModel.saveEvent(geo, milliseconds)
                }
            }
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            val activity = activity as? AppActivity
            activity?.findViewById<ImageView>(R.id.profile)?.visibility = View.VISIBLE
            //activity?.findViewById<ImageView>(R.id.log_in)?.visibility = View.VISIBLE
        }
        return binding.root
    }
}