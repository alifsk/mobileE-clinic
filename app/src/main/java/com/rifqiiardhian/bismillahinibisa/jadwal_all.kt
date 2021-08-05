package com.rifqiiardhian.bismillahinibisa

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.Toast
import org.json.JSONObject
import java.util.ArrayList

class jadwal_all : Fragment() {

    var view1: View? = null
    private lateinit var daftar_jadwal: ListView
    var data_jadwal: ArrayList<model_jadwal> = ArrayList<model_jadwal>()

    private val url = "http://eclinic.rifqiiardhian.my.id/api/Dokterapi.php"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_jadwal_all, container, false)

        daftar_jadwal = view1!!.findViewById(R.id.daftar_jadwal)

        loaddatajadwalall()

        return view1
    }

    companion object {
        fun newInstance(): jadwal_all {
            return jadwal_all()
        }
    }

    fun loaddatajadwalall() { RequestAsnycJadwalAll().execute() }

    inner class RequestAsnycJadwalAll : AsyncTask<String?, String?, String?>() {
        var progress_load: RelativeLayout? = null
        var background_load: RelativeLayout? = null

        override fun onPreExecute() {
            super.onPreExecute()

            background_load = view1!!.findViewById(R.id.backgroud_loading_jadwal)
            background_load!!.visibility = View.VISIBLE

            progress_load = view1!!.findViewById(R.id.progress_loading_jadwal)
            progress_load!!.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg strings: String?): String? {
            return try {
                // POST Request
                val postDataParams = JSONObject()
                postDataParams.put("method", "JadwalPraktik")

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

                            val id_dokter = obj1.getString("id_dokter")
                            val nama_dokter = obj1.getString("nama_dokter")
                            val gelar_depan = obj1.getString("gelar_depan")
                            val gelar_belakang = obj1.getString("gelar_belakang")
                            val gender = obj1.getString("gender")
                            val poli = obj1.getString("poli")
                            val hari_praktik = obj1.getString("hari_praktik")
                            val jam_praktik = obj1.getString("jam_praktik")

                            data_jadwal.add(model_jadwal(id_dokter,nama_dokter, gelar_depan, gelar_belakang, gender, poli, hari_praktik, jam_praktik))
                            val customAdapterJadwal = CustomAdapterJadwal(context!!, data_jadwal)
                            daftar_jadwal.adapter = customAdapterJadwal
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
