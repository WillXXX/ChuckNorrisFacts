package com.example.chucknorrisfacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    //**Fazendo chamadas Json web service, usando a biblioteca Okhttp3
    // Os fatos serão obtidos no banco de dados do Internet Chuck Norris,
    // que oferece uma API simples para obter fatos aleatórios do Chuck Norris:

    val URL = "https://api.icndb.com/jokes/random"
    var okHttpClient: OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nextBtn.setOnClickListener{
            loadRandomFact()
        }
   }

    private fun loadRandomFact(){
        runOnUiThread {
            progressBar.visibility = View.VISIBLE
        }

        val request: Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                android.util.Log.d( "APP","Falha ao solicitar requisição")
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body()?.string()

                val txt = (JSONObject (json).getJSONObject("value").get("joke")).toString()

                runOnUiThread{
                    progressBar.visibility = View.GONE
                    factTv.text = Html.fromHtml(txt)
                }
            }
        })

    }

}
