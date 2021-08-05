package com.rifqiiardhian.bismillahinibisa

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import org.json.JSONObject

class detail_obat : AppCompatActivity(), View.OnClickListener {

    private lateinit var back: Button
    private lateinit var kembali: Button
    private lateinit var text_nama: TextView
    private lateinit var text_kegunaan: TextView
    private lateinit var text_dosis: TextView
    private lateinit var text_efek: TextView
    private lateinit var text_peringatan: TextView
    private lateinit var text_keterangan: TextView
    private var id_obat_detail: String? = null
    private val url = "http://eclinic.rifqiiardhian.my.id/api/Obatapi.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_obat)

        val intent = intent
        id_obat_detail = intent.getStringExtra("id_obat_detail")

        text_nama = findViewById(R.id.title)
        text_kegunaan = findViewById(R.id.kegunaan_obat)
        text_dosis = findViewById(R.id.dosis_obat)
        text_efek = findViewById(R.id.efek_samping)
        text_peringatan = findViewById(R.id.peringatan_obat)
        text_keterangan = findViewById(R.id.keterangan_obat)

        back = findViewById(R.id.back)
        kembali = findViewById(R.id.kembali)
        back.setOnClickListener(this)
        kembali.setOnClickListener(this)

        loaddatadetail()
    }

    companion object{}

    override fun onClick(view: View) {
        if ( view.id == R.id.back || view.id == R.id.kembali ) {
            finish()
        }
    }

    fun loaddatadetail() { RequestAsyncObatDetail().execute() }

    inner class RequestAsyncObatDetail: AsyncTask<String?, String?, String?>() {
        var progress_load: RelativeLayout? = null
        var background_load: RelativeLayout? = null

        override fun onPreExecute() {
            super.onPreExecute()

            background_load = findViewById(R.id.backgroud_loading_obat)
            background_load!!.visibility = View.VISIBLE

            progress_load = findViewById(R.id.progress_loading_obat)
            progress_load!!.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg strings: String?): String? {
            return try {
                // POST Request
                val postDataParams = JSONObject()
                postDataParams.put("id_detail", id_obat_detail)
                postDataParams.put("method", "DetailObat")

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

                            text_nama.text = obj1.getString("nama_obat")
                            text_kegunaan.text = obj1.getString("kegunaan")
                            text_dosis.text = obj1.getString("dosis")
                            text_efek.text = obj1.getString("efek_samping")
                            text_peringatan.text = obj1.getString("peringatan")
                            text_keterangan.text = obj1.getString("keterangan")
                        }
                    } else {
                        val message: String = obj.getString("message")
                        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "HALO $e", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(applicationContext, "NO", Toast.LENGTH_LONG).show()
            }
        }
    }
}
