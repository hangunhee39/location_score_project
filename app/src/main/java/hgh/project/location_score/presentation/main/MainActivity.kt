package hgh.project.location_score.presentation.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.tasks.CancellationTokenSource
import hgh.project.location_score.data.entity.SearchResult
import hgh.project.location_score.databinding.ActivityMainBinding
import hgh.project.location_score.presentation.BaseActivity
import hgh.project.location_score.presentation.result.ResultActivity
import org.koin.android.ext.android.inject

internal class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel by inject<MainViewModel>()

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun observeData() = viewModel.mainStateLiveData.observe(this) {
        when (it) {
            is MainState.Uninitialized -> {
                initViews()
            }
            is MainState.Loading -> {
                handleLoading()
            }
            is MainState.Success -> {
                handleSuccess(it)
            }
            is MainState.Error -> {
                handleError()
            }
        }
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var cancellationTokenSource: CancellationTokenSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = getFusedLocationProviderClient(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource?.cancel()
    }

    private fun initViews() = with(binding) {
        locationButton.setOnClickListener {
            requestLocationPermissions()
        }
    }

    private fun handleLoading() {

    }

    private fun handleSuccess(state: MainState.Success) {
        startActivity(
            ResultActivity.newIntent(baseContext, state.result)
        )
    }

    private fun handleError() {
        Toast.makeText(baseContext, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_ACCESS_LOCATION_PERMISSIONS
            )
        } else {
            getLocationScore()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_ACCESS_LOCATION_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                getLocationScore()
            } else {
                Toast.makeText(this, "권한을 받지 못했습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocationScore() {
        cancellationTokenSource = CancellationTokenSource()

        fusedLocationProviderClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource!!.token
        ).addOnSuccessListener { location ->
            viewModel.searchResult(location.latitude.toString(), location.latitude.toString())
        }
    }

    companion object {
        const val REQUEST_ACCESS_LOCATION_PERMISSIONS = 101
    }
}