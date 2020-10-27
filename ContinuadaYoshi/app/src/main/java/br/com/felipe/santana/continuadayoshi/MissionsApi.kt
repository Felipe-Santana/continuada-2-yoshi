package br.com.felipe.santana.continuadayoshi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MissionsApi {
    @GET("/agents/{id}/missions")
    fun getMissions(@Path("id") id: Int): Call<List<Mission>>
}