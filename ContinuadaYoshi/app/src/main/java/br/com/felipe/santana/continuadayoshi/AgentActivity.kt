package br.com.felipe.santana.continuadayoshi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agent.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AgentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent)

        val agent = intent.getParcelableExtra<Agent>("AGENT")

        tv_agent_name.text = agent?.name
        tv_agent_name.setTextColor(Color.parseColor(agent?.access_color))

        tv_agent_access_level.text = agent?.access_code
        tv_agent_access_level.setTextColor(Color.parseColor(agent?.access_color))

        tv_agent_email.text = agent?.email

        tv_agent_location.text = agent?.location

        getAgentMissions(agent?.id.toString().toInt())
    }

    private fun getAgentMissions(id: Int) {
        val retrofit =
            Retrofit.Builder().baseUrl("https://5f879ec749ccbb0016177719.mockapi.io/v1/")
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()

        val missionsApiClient: MissionsApi? = retrofit.create(MissionsApi::class.java)

        missionsApiClient?.getMissions(id)?.enqueue(object : Callback<List<Mission>> {
            override fun onResponse(call: Call<List<Mission>>, response: Response<List<Mission>>) {
                response.body()?.forEach {
                    val missionTv = TextView(baseContext)

                    missionTv.text = getString(R.string.mission, it.name, it.code, it.location)
                    missionTv.setTextAppearance(R.style.Mission)

                    missions_layout.addView(missionTv)
                }
            }

            override fun onFailure(call: Call<List<Mission>>, t: Throwable) {
                Log.e("API_ERROR", getString(R.string.unable_to_get_missions), t)
                Toast.makeText(
                    baseContext,
                    getString(R.string.unable_to_get_missions),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}