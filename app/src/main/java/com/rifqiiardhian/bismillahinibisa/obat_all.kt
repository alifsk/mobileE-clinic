package com.rifqiiardhian.bismillahinibisa

import android.content.Intent
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

class obat_all : Fragment() {

    var view1: View? = null

    private lateinit var daftar_obat: ListView
    var data_obat: ArrayList<model_obat> = ArrayList<model_obat>()
    var ID_OBAT = "id_obat_detail"

    private val url = "http://eclinic.rifqiiardhian.my.id/api/Obatapi.php"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_obat_all, container, false)

        daftar_obat = view1!!.findViewById(R.id.daftar_obat)

        loaddataobatall()

        daftar_obat.setOnItemClickListener { _, _, i, _ ->
            val id_obat_selected = data_obat[i].id_obat.toString()
            val intent = Intent(context, detail_obat::class.java)
            intent.putExtra(ID_OBAT, id_obat_selected)
            startActivity(intent)
        }

        return view1
    }

    companion object {
        fun newInstance(): obat_all {
            return obat_all()
        }
    }

    fun loaddataobatall() { RequestAsnycObatAll().execute() }

    inner class RequestAsnycObatAll : AsyncTask<String?, String?, String?>() {
        var progress_load: RelativeLayout? = null
        var background_load: RelativeLayout? = null

        override fun onPreExecute() {
            super.onPreExecute()

            background_load = view1!!.findViewById(R.id.backgroud_loading_obat)
            background_load!!.visibility = View.VISIBLE

            progress_load = view1!!.findViewById(R.id.progress_loading_obat)
            progress_load!!.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg strings: String?): String? {
            return try {
                // POST Request
                val postDataParams = JSONObject()
                postDataParams.put("method", "SelectObatAll")

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

                            val id_obat = obj1.getString("id_obat")
                            val nama_obat = obj1.getString("nama_obat")

                            data_obat.add(model_obat(id_obat, nama_obat))
                            val customAdapterObat = CustomAdapterObat(context!!, data_obat)
                            daftar_obat.adapter = customAdapterObat
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
