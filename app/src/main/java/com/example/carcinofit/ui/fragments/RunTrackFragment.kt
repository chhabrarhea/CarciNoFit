package com.example.carcinofit.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carcinofit.R
import com.example.carcinofit.databinding.DialogGenericBinding
import com.example.carcinofit.other.Constants.ACTION_PAUSE_SERVICE
import com.example.carcinofit.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.carcinofit.other.Constants.ACTION_STOP_SERVICE
import com.example.carcinofit.other.Constants.MAP_ZOOM
import com.example.carcinofit.other.Constants.POLYLINE_COLOR
import com.example.carcinofit.other.Constants.POLYLINE_WIDTH
import com.example.carcinofit.other.TrackingUtility
import com.example.carcinofit.services.PolyLine
import com.example.carcinofit.services.TrackingService
import com.example.carcinofit.ui.viewmodels.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_run_track.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import timber.log.Timber
import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class RunTrackFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var isTracking = false
    private var pathPoints = mutableListOf<PolyLine>()
    private var map: GoogleMap? = null
    private var curTimeInMillis = 0L
    private var weight = 80

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions.containsValue(false)) {

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_run_track, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        checkPermissions()
        mapView.getMapAsync {
            map = it
            drawAllPolyLines()
        }
        subscribeToTrackingService()
        btnToggleRun.setOnClickListener { toggleRun() }
        cancelBtn.setOnClickListener { showCancelTrackingDialog() }
        btnFinishRun.setOnClickListener { endRunAndSaveToDb() }
    }

    //polyLines have to be drawn again when screen reconfiguration occurs.
    private fun drawAllPolyLines() {
        for (polyline in pathPoints) {
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }

    //Move mapView to users' last known location
    private fun moveCameraToUser() {
        if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(pathPoints.last().last(), MAP_ZOOM)
            )
        }
    }

    private fun zoomToSeeWholeTrack() {
        if(pathPoints.isEmpty() || pathPoints[0].isEmpty())
            return
        val bounds = LatLngBounds.Builder()
        for (polyline in pathPoints) {
            for (pos in polyline) {
                bounds.include(pos)
                Timber.i(pos.toString())
            }
        }
        map?.moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds.build(),
                mapView.width,
                mapView.height,
                (mapView.height * 0.05f).toInt()
            )
        )
    }

    private fun endRunAndSaveToDb() {
            zoomToSeeWholeTrack()
        map?.snapshot { bmp ->
            var distanceInMeters = 0
            for (polyline in pathPoints) {
                distanceInMeters += TrackingUtility.calculatePolylineLength(polyline).toInt()
            }
            if(distanceInMeters < 500) {
                showShortDialog()
                return@snapshot
            }
            val avgSpeed =
                ((distanceInMeters / 1000f) / (curTimeInMillis / 1000f / 60 / 60) * 10).roundToInt() / 10f
            val dateTimestamp = Date(Calendar.getInstance().timeInMillis)
            val caloriesBurned = ((distanceInMeters / 1000f) * weight).toInt()
            viewModel.insertWorkout(
                0,
                dateTimestamp,
                curTimeInMillis,
                caloriesBurned,
                avgSpeed,
                distanceInMeters,
                bmp
            )
            Snackbar.make(
                requireActivity().findViewById(R.id.rootView),
                "Run saved successfully",
                Snackbar.LENGTH_LONG
            ).show()
            stopRun()
        }
    }

    private fun showShortDialog() {
        val dialogBinding = DialogGenericBinding.inflate(layoutInflater, null, false)
        val alertDialog = MaterialAlertDialogBuilder(requireContext()).create()
        dialogBinding.captionTv.text = "Cannot store runs shorter than 500m"
        dialogBinding.confirmButton.apply {
            text = "Continue"
            setOnClickListener {
                alertDialog.dismiss()
            }
        }
        dialogBinding.cancelButton.apply {
            text = "Abort"
            setOnClickListener {
                stopRun()
                alertDialog.dismiss()
            }
        }
        alertDialog.apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setView(dialogBinding.root)
            setCancelable(false)
        }
        alertDialog.show()
    }


    private fun subscribeToTrackingService() {
        TrackingService.pathPoints.observe(viewLifecycleOwner, {
            this.pathPoints = it
            addLatestPolyline()
            moveCameraToUser()
        })
        TrackingService.isTracking.observe(viewLifecycleOwner, {
            updateTracking(it)
        })
        TrackingService.timeRunInMillis.observe(viewLifecycleOwner, {
            curTimeInMillis = it
            val formattedTime = TrackingUtility.getFormattedStopWatchTime(curTimeInMillis, true)
            tvTimer.text = formattedTime
        })
    }

    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if (!isTracking) {
            btnToggleRun.text = getString(R.string.start)
            btnFinishRun.visibility = View.VISIBLE
        } else {
            btnToggleRun.text = getString(R.string.stop)
            btnFinishRun.visibility = View.GONE
        }
    }

    private fun toggleRun() {
        btnFinishRun.isVisible = true
        if (isTracking) {
            sendCommandToService(ACTION_PAUSE_SERVICE)
        } else {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun showCancelTrackingDialog() {
        val dialog = MaterialAlertDialogBuilder(
            requireContext(),
            R.style.ThemeOverlay_AppCompat_Dialog_Alert
        )
            .setTitle("Cancel the Run?")
            .setMessage("Are you sure to cancel the current run and delete all its data?")
            .setPositiveButton("Yes") { _, _ ->
                stopRun()
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
        dialog.show()
    }

    private fun stopRun() {
        sendCommandToService(ACTION_STOP_SERVICE)
        findNavController().navigate(R.id.action_runTrackFragment_to_homeFragment)
    }

    private fun sendCommandToService(action: String) {
        requireContext().startService(Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
        })
    }


    private fun addLatestPolyline() {
        if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            val preLast = pathPoints.last()[pathPoints.last().size - 2]
            val last = pathPoints.last().last()
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLast)
                .add(last)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun checkPermissions() {
        if (!TrackingUtility.hasLocationPermissions(requireContext())) {
            requestPermissionLauncher.launch(TrackingUtility.locationPermissions())
        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}

