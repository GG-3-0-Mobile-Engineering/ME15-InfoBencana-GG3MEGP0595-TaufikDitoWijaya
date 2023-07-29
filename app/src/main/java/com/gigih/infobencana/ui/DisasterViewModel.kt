package com.gigih.infobencana.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gigih.infobencana.data.DisasterReports
import com.gigih.infobencana.data.DisasterResponse
import com.gigih.infobencana.data.Geometry
import com.gigih.infobencana.networking.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DisasterViewModel: ViewModel() {
    private val _disasterCoordinate = MutableLiveData<List<Geometry>?>()
    val disasterCoordinate: LiveData<List<Geometry>?> = _disasterCoordinate
    private val _disasterReport = MutableLiveData<List<DisasterReports>?>()
    val disasterReports: LiveData<List<DisasterReports>?> = _disasterReport

    fun getDisasterCoordinates(timePeriod: Number) {
        ApiClient.instance.getDisasterAll(timePeriod).enqueue(object : Callback<DisasterResponse> {
            override fun onResponse(
                call: Call<DisasterResponse>,
                response: Response<DisasterResponse>
            ) {
                if (response.isSuccessful) {
                    val coordinates = response.body()?.result?.objects?.output?.geometries
                    _disasterCoordinate.value = coordinates
                    _disasterReport.value = coordinates?.map { it.disasterReports }
                }
            }

            override fun onFailure(call: Call<DisasterResponse>, t: Throwable) {
                t.message?.let { Log.d("Failed to Load!", it) }
            }
        })
    }
}