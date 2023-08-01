package com.gigih.infobencana.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gigih.infobencana.R
import com.gigih.infobencana.data.DisasterReports

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gigih.infobencana.databinding.ActivityMapsBinding
import com.gigih.infobencana.ui.setting.SettingActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var viewModel: DisasterViewModel
    private lateinit var adapter: DisasterAdapter
    private lateinit var bottomsheet: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DisasterViewModel::class.java]

        viewModel.disasterReports.observe(this) {report ->
            if (report != null) {
                adapter = DisasterAdapter(report)
                showBottomSheet(report)
            }
        }

        binding.buttonDialog.setOnClickListener {
            bottomsheet.show()
        }

        binding.btnSetting.setOnClickListener {
            val intent = Intent(this@MapsActivity, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showBottomSheet(report: List<DisasterReports>) {
        val bottomSheet = layoutInflater.inflate(R.layout.bottomsheet, null)
        val recyclerView = bottomSheet.findViewById<RecyclerView>(R.id.rv_disaster)
        bottomsheet = BottomSheetDialog(this)
        bottomsheet.setContentView(bottomSheet)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DisasterAdapter(report)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        viewModel.getDisasterCoordinates(604800)
        viewModel.disasterCoordinate.observe(this) {
            it?.forEach { coordinates ->
                if (coordinates.type == "Point") {
                    val data = coordinates.coordinates
                    val latitude = data[1]
                    val longitude = data[0]
                    val markers = LatLng(latitude, longitude)
                    googleMap.addMarker(MarkerOptions()
                        .position(markers)
                        .title(coordinates.disasterReports.disasterType)
                        .snippet("Bencana Terjadi Pada: ${coordinates.disasterReports.createdAt}"))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers, 10f))
                }
            }
        }
    }
}