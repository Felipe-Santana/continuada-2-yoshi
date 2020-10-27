package br.com.felipe.santana.continuadayoshi

import retrofit2.Call
import retrofit2.http.GET

interface AgentsApi {
    @GET("/agents")
    fun getAgents(): Call<List<Agent>>
}