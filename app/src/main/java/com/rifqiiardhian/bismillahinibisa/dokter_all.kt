package com.rifqiiardhian.bismillahinibisa

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class dokter_all : Fragment() {

    var view1: View? = null
    private lateinit var daftar_dokter: RecyclerView
    var data_dokter: ArrayList<model_dokter> = ArrayList<model_dokter>()
    var ID_DOKTER = "id_dokter_detail"
    private val url = "http://eclinic.rifqiiardhian.my.id/api/Dokterapi.php"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_dokter_all, container, false)

        daftar_dokter = view1!!.findViewById(R.id.widget_dokter)
        val linearLayoutManager = LinearLayoutManager(context)
        daftar_dokter.layoutManager = linearLayoutManager

        loaddatadokterall()

        return view1
    }

    companion object {
        fun newInstance(): dokter_all {
            return dokter_all()
        }
    }

    fun loaddatadokterall() { RequestAsnycDokterAll().execute() }

    inner class RequestAsnycDokterAll : AsyncTask<String?, String?, String?>(),
        CustomAdapterDokter.OnDokterListener {
        var progress_load: RelativeLayout? = null
        var background_load: RelativeLayout? = null

        override fun onPreExecute() {
            super.onPreExecute()

            background_load = view1!!.findViewById(R.id.backgroud_loading_dokter)
            background_load!!.visibility = View.VISIBLE

            progress_load = view1!!.findViewById(R.id.progress_loading_dokter)
            progress_load!!.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg strings: String?): String? {
            return try {
                // POST Request
                val postDataParams = JSONObject()
                postDataParams.put("method", "SelectDokterAll")

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

                            data_dokter.add(model_dokter(id_dokter,nama_dokter, gelar_depan, gelar_belakang, gender, poli, hari_praktik, jam_praktik))
                            val data = CustomAdapterDokter(context!!, data_dokter, this)
                            daftar_dokter.adapter = data
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

        override fun onDokterClick(position: Int) {
            val id_dokter = data_dokter[position].id_dokter.toString()

            val intent = Intent(context, detail_dokter::class.java)
            intent.putExtra(ID_DOKTER, id_dokter)
            startActivity(intent)
        }
    }
}
