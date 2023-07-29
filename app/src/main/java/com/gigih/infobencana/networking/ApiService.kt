package com.gigih.infobencana.networking

import com.gigih.infobencana.data.DisasterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("reports")
    fun getDisasterAll(
        @Query("timeperiod") number: Number
    ): Call<DisasterResponse>
}