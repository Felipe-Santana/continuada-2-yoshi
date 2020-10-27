package br.com.felipe.santana.continuadayoshi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var agents: List<Agent>
    private var agent: Agent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeAgents()
    }

    fun accessSystem(view: View) {
        val accessCode = agent_code.text.toString()
        val password = agent_password.text.toString()

        when {
            accessCode.isBlank() -> {
                Toast.makeText(
                    baseContext,
                    getString(R.string.access_code_required),
                    Toast.LENGTH_SHORT
                )
                    .show()
                return
            }
            password.isBlank() -> {
                Toast.makeText(
                    baseContext,
                    getString(R.string.password_is_required),
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
        }

        agents.forEach {
            if (it.access_code == accessCode && it.password == password) {
                agent = it
            }
        }

        if (agent == null) {
            Toast.makeText(baseContext, getString(R.string.access_denied), Toast.LENGTH_LONG).show()
            return
        }

        val intent = Intent(this, AgentActivity::class.java)

        intent.putExtra("AGENT", agent)

        startActivity(intent)
    }

    private fun initializeAgents() {
        val retrofit =
            Retrofit.Builder().baseUrl("https://5f879ec749ccbb0016177719.mockapi.io/v1/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()

        val agentsApiClient: AgentsApi? = retrofit.create(AgentsApi::class.java)

        agentsApiClient?.getAgents()?.enqueue(object : Callback<List<Agent>> {
            override fun onResponse(call: Call<List<Agent>>, response: Response<List<Agent>>) {
                agents = response.body()!!
            }

            override fun onFailure(call: Call<List<Agent>>, t: Throwable) {
                Log.e("API_ERROR", getString(R.string.unable_to_get_agents), t)
                Toast.makeText(
                    baseContext,
                    getString(R.string.unable_to_get_agents),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}