package com.rifqiiardhian.bismillahinibisa

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import org.json.JSONObject

class antrianterbaru : Fragment() {

    var view1: View? = null

    private lateinit var daftar_antrian: ListView
    var data_antrian: ArrayList<model_antrian> = ArrayList<model_antrian>()
    var session: SessionManager? = null
    private var id_user: String? = null
    var ID_ANTRIAN = "id_detail_antrian"

    private val url = "http://eclinic.rifqiiardhian.my.id/api/Antrianapi.php"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view1 = inflater.inflate(R.layout.fragment_antrianterbaru, container, false)

        session = SessionManager(context!!)
        val user: HashMap<String, String?> = session!!.dataUser

        id_user = user[SessionManager.ID_USER]

        daftar_antrian = view1!!.findViewById(R.id.daftar_antrian)

        loaddataantrianterbaru()

        daftar_antrian.setOnItemClickListener { _, _, i, _ ->
            val id_antrian_selected = data_antrian[i].id_antrian.toString()
            val intent = Intent(context, detail_antrian::class.java)
            intent.putExtra(ID_ANTRIAN, id_antrian_selected)
            startActivity(intent)
        }

        return view1
    }

    companion object {
        fun newInstance(): antrianterbaru {
            return antrianterbaru()
        }
    }

    fun loaddataantrianterbaru() { RequestAsnycAntrianTerbaru().execute() }

    inner class RequestAsnycAntrianTerbaru : AsyncTask<String?, String?, String?>() {

        override fun doInBackground(vararg strings: String?): String? {
            return try {
                // POST Request
                val postDataParams = JSONObject()
                postDataParams.put("id_user", id_user)
                postDataParams.put("method", "AntrianTerbaru")

                RequestHandler.sendPost(url, postDataParams)
            } catch (e: Exception) {
                "Exception ini saya coba: " + e.message
            }
        }

        override fun onPostExecute(result: String?) {
            if ( result != null ) {
                try {
                    val obj = JSONObject(result)
                    val success: Int = obj.getInt("success")

                    if ( success == 1 ) {
                        val jArray = obj.getJSONArray("data")

                        for (i in 0 until jArray.length()) {
                            val obj1 = jArray.getJSONObject(i)

                            val id_antrian = obj1.getString("id_antrian")
                            val tanggal = obj1.getString("tanggal")
                            val status = obj1.getString("status")
                            val nama_dokter = obj1.getString("nama_dokter")
                            val gelar_depan = obj1.getString("gelar_depan")
                            val gelar_belakang = obj1.getString("gelar_belakang")
                            val poli = obj1.getString("poli")
                            val hari_praktik = obj1.getString("hari")
                            val jam_praktik = obj1.getString("jam")

                            data_antrian.add(model_antrian(id_antrian, tanggal, status, nama_dokter, gelar_depan, gelar_belakang, poli, hari_praktik, jam_praktik))
                            val customAdapterAntrian = CustomAdapterAntrian(context!!, data_antrian)
                            daftar_antrian.adapter = customAdapterAntrian
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
