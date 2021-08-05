package com.rifqiiardhian.bismillahinibisa

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import org.json.JSONObject
import java.util.*

class penyakit_all : Fragment() {

    var view1: View? = null

    private lateinit var daftar_penyakit: ListView
    var data_penyakit: ArrayList<model_penyakit> = ArrayList<model_penyakit>()
    var ID_PENYAKIT = "id_penyakit_detail"

    private val url = "http://eclinic.rifqiiardhian.my.id/api/Penyakitapi.php"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_penyakit_all, container, false)

        daftar_penyakit = view1!!.findViewById(R.id.daftar_penyakit)

        daftar_penyakit.setOnItemClickListener { _, _, i, _ ->
            val id_penyakit_selected = data_penyakit[i].id_penyakit.toString()
            val intent = Intent(context, detail_penyakit::class.java)
            intent.putExtra(ID_PENYAKIT, id_penyakit_selected)
            startActivity(intent)
        }

        loaddatapenyakitall()

        return view1
    }

    companion object {
        fun newInstance(): penyakit_all {
            return penyakit_all()
        }
    }

    fun loaddatapenyakitall() { RequestAsnycPenyakitAll().execute() }

    inner class RequestAsnycPenyakitAll : AsyncTask<String?, String?, String?>() {
        var progress_load: RelativeLayout? = null
        var background_load: RelativeLayout? = null

        override fun onPreExecute() {
            super.onPreExecute()

            background_load = view1!!.findViewById(R.id.backgroud_loading_penyakit)
            background_load!!.visibility = View.VISIBLE

            progress_load = view1!!.findViewById(R.id.progress_loading_penyakit)
            progress_load!!.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg strings: String?): String? {
            return try {
                // POST Request
                val postDataParams = JSONObject()
                postDataParams.put("method", "SelectPenyakitAll")

                RequestHandler.sendPost(url, postDataParams)
            } catch (e: Exception) {
                "Exception ini saya coba: " + e.message
            }
        }

        override fun onPostExecute(result: String?) {
            background_load!!.visibility = View.GONE
            progress_load!!.visibility = View.GONE

            if ( result != null ) {
                try {
                    val obj = JSONObject(result)
                    val success: Int = obj.getInt("success")

                    if ( success == 1 ) {
                        val jArray = obj.getJSONArray("data")

                        for (i in 0 until jArray.length()) {
                            val obj1 = jArray.getJSONObject(i)

                            val id_penyakit = obj1.getString("id_penyakit")
                            val nama_penyakit = obj1.getString("nama_penyakit")
                            val kategori_penyakit = obj1.getString("kategori_penyakit")

                            data_penyakit.add(model_penyakit(id_penyakit, nama_penyakit, kategori_penyakit))
                            val customAdapterPenyakit = CustomAdapterPenyakit(context!!, data_penyakit)
                            daftar_penyakit.adapter = customAdapterPenyakit
                        }
                    } else {
                        val message: String = obj.getString("message")
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "HALO $e", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "NO", Toast.LENGTH_LONG).show()
            }
        }
    }
}
