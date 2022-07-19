package hgh.project.location_score.presentation.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.tasks.CancellationTokenSource
import hgh.project.location_score.databinding.ActivityMainBinding
import hgh.project.location_score.presentation.BaseActivity
import hgh.project.location_score.presentation.adapter.HistoryListAdapter
import hgh.project.location_score.presentation.result.ResultActivity
import org.koin.android.ext.android.inject

internal class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel by inject<MainViewModel>()

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    private val adapter = HistoryListAdapter()

    override fun observeData() = viewModel.mainStateLiveData.observe(this) {
        when (it) {
            is MainState.Uninitialized -> {
                initViews()
            }
            is MainState.Loading -> {
                handleLoading()
            }
            is MainState.Success ->{
                handleSuccess(it)
            }
            is MainState.SearchLoading -> {
                handleSearchLoading()
            }
            is MainState.SearchSuccess -> {
                handleSearchSuccess(it)
            }
            is MainState.Error -> {
                handleError()
            }
        }
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var cancellationTokenSource: CancellationTokenSource? = null

    private val startResultActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == ResultActivity.RESULT_CODE){
                viewModel.fetchData()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = getFusedLocationProviderClient(this@MainActivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource?.cancel()
    }

    private fun initViews() = with(binding) {
        historyRecyclerView.adapter = adapter
        searchButton.setOnClickListener {
            requestLocationPermissions()
        }
        swipeRefresh.setOnRefreshListener {
            viewModel.fetchData()
        }
    }

    private fun handleLoading(){
        binding.swipeRefresh.isRefreshing = true
    }

    private fun handleSuccess(state: MainState.Success){
        binding.swipeRefresh.isRefreshing =false

        adapter.setListAdapter(state.historyList) {
            viewModel.deleteHistory(it)
        }
    }

    private fun handleSearchLoading() {

    }

    private fun handleSearchSuccess(state: MainState.SearchSuccess) {
        startResultActivityForResult.launch(
            ResultActivity.newIntent(this, state.result)
        )
    }

    private fun handleError() {
        Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
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
            viewModel.searchResult(location.longitude.toString(), location.latitude.toString())
        }
    }


    companion object {
        const val REQUEST_ACCESS_LOCATION_PERMISSIONS = 101
    }

}