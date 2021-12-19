package com.chuify.cleanxoomclient.presentation

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.databinding.ActivityMapsBinding
import com.chuify.cleanxoomclient.domain.usecase.location.SaveLocationsUseCase
import com.chuify.cleanxoomclient.domain.utils.DataState
import com.chuify.cleanxoomclient.presentation.PermissionUtils.RationaleDialog.Companion.newInstance
import com.chuify.cleanxoomclient.presentation.PermissionUtils.isPermissionGranted
import com.chuify.cleanxoomclient.presentation.PermissionUtils.requestPermission
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

private const val TAG = "MyLocationDemoActivity"

/**
 * This demo shows how GMS Location can be used to check for changes to the users location.  The
 * "My Location" button uses GMS Location to set the blue dot representing the users location.
 * Permission for [Manifest.permission.ACCESS_FINE_LOCATION] is requested at run
 * time. If the permission has not been granted, the Activity is finished with an error message.
 */
@AndroidEntryPoint
class LocationActivity : AppCompatActivity(), OnMyLocationButtonClickListener,
    OnMyLocationClickListener, OnMapReadyCallback, OnRequestPermissionsResultCallback {
    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * [.onRequestPermissionsResult].
     */

    @Inject
    lateinit var saveLocationsUseCase: SaveLocationsUseCase
    private var permissionDenied = false
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var location: LatLng


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val configuration: Configuration = resources.configuration
        configuration.fontScale = 1.toFloat() //0.85 small size, 1 normal size, 1,15 big etc

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        configuration.densityDpi = resources.displayMetrics.xdpi.toInt()
        baseContext.resources.updateConfiguration(configuration, metrics)
        val progressDialog = ProgressDialog(this@LocationActivity)
        progressDialog.setTitle("Loading")
        progressDialog.setCancelable(false)
        val builder = AlertDialog.Builder(this@LocationActivity)
            .setPositiveButton(android.R.string.yes) { dialog, _ ->
                dialog.dismiss()
            }.create()

        binding = ActivityMapsBinding.inflate(layoutInflater)
        binding.btnConfirmLocation.setOnClickListener {
            binding.layoutAddLocation.visibility = View.VISIBLE
            binding.tvTitle.requestFocus()
            binding.btnConfirmLocation.visibility = View.GONE
        }
        binding.btnSaveLocation.setOnClickListener {

            lifecycleScope.launchWhenCreated {
                saveLocationsUseCase(
                    addressUrl = binding.tvTitle.text.toString(),
                    details = binding.tvDetails.text.toString(),
                    instructions = binding.tvInstructions.text.toString(),
                    lat = location.latitude,
                    lng = location.longitude,
                ).collect { dataState ->
                    when (dataState) {
                        is DataState.Error -> {
                            progressDialog.dismiss()
                            dataState.message?.let {
                                builder.setMessage(it)
                                builder.show()
                            }
                        }
                        is DataState.Loading -> {
                            progressDialog.show()
                            builder.dismiss()
                        }
                        is DataState.Success -> {
                            progressDialog.dismiss()
                            builder.dismiss()
                            Toast.makeText(
                                this@LocationActivity,
                                "Successfully added",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
                }
            }
        }
        binding.imgBack.setOnClickListener { finish() }
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true

            map.setOnCameraMoveStartedListener {
                binding.layoutAddLocation.visibility = View.GONE
                binding.btnConfirmLocation.visibility = View.VISIBLE
                binding.btnSaveLocation.isEnabled = false
            }
            map.setOnCameraIdleListener {
                binding.btnConfirmLocation.visibility = View.VISIBLE
                binding.btnSaveLocation.isEnabled = true
            }

            if (map.myLocation == null) {
                this.location = LatLng(
                    -1.2846009,
                    36.8201749
                )
            } else {
                this.location = LatLng(
                    map.myLocation.latitude,
                    map.myLocation.longitude
                )
            }

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 18F))
            map.setOnCameraChangeListener { cameraPosition ->
                map.clear()
                try {
                    location = cameraPosition.target
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        } else {
            // Permission to access the location is missing. Show rationale and request permission
            requestPermission(
                this, LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION, true
            )
        }
        // [END maps_check_location_permission]
    }

    override fun onMyLocationButtonClick(): Boolean {
        // Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG).show()
    }

    // [START maps_check_location_permission_result]
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }
        if (isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation()
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
            // [END_EXCLUDE]
        }
    }

    // [END maps_check_location_permission_result]
    override fun onResumeFragments() {
        super.onResumeFragments()
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError()
            permissionDenied = false
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private fun showMissingPermissionError() {
        newInstance(10, true).show(supportFragmentManager, "dialog")
    }

    companion object {
        /**
         * Request code for location permission request.
         *
         * @see .onRequestPermissionsResult
         */
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        enableMyLocation()
    }

}
