package com.rifqiiardhian.bismillahinibisa

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import org.json.JSONObject

class detail_penyakit : AppCompatActivity(), View.OnClickListener {

    private lateinit var back: Button
    private lateinit var kembali: Button
    private lateinit var text_nama: TextView
    private lateinit var text_kategori: TextView
    private lateinit var text_deskripsi: TextView
    private lateinit var text_gejala: TextView
    private lateinit var text_penyebab: TextView
    private lateinit var text_pengobatan: TextView
    private lateinit var text_pencegahan: TextView
    private var id_penyakit_detail: String? = null
    private val url = "http://eclinic.rifqiiardhian.my.id/api/Penyakitapi.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_penyakit)

        val intent = intent
        id_penyakit_detail = intent.getStringExtra("id_penyakit_detail")

        text_nama = findViewById(R.id.title)
        text_kategori = findViewById(R.id.kategori_penyakit)
        text_deskripsi = findViewById(R.id.deskripsi_penyakit)
        text_penyebab = findViewById(R.id.penyebab_penyakit)
        text_gejala = findViewById(R.id.gejala_penyakit)
        text_pengobatan = findViewById(R.id.pengobatan_penyakit)
        text_pencegahan = findViewById(R.id.pencegahan_penyakit)

        loaddatadetail()

        back = findViewById(R.id.back)
        kembali = findViewById(R.id.kembali)
        back.setOnClickListener(this)
        kembali.setOnClickListener(this)
    }

    companion object {}

    override fun onClick(view: View) {
        if ( view.id == R.id.back || view.id == R.id.kembali ) {
            finish()
        }
    }

    fun loaddatadetail() { RequestAsyncPenyakitDetail().execute() }

    inner class RequestAsyncPenyakitDetail: AsyncTask<String?, String?, String?>() {
        var progress_load: RelativeLayout? = null
        var background_load: RelativeLayout? = null

        override fun onPreExecute() {
            super.onPreExecute()

            background_load = findViewById(R.id.backgroud_loading_penyakit)
            background_load!!.visibility = View.VISIBLE

            progress_load = findViewById(R.id.progress_loading_penyakit)
            progress_load!!.visibility = View.VISIBLE
        }
        override fun doInBackground(vararg strings: String?): String? {
            return try {
                // POST Request
                val postDataParams = JSONObject()
                postDataParams.put("id_detail", id_penyakit_detail)
                postDataParams.put("method", "DetailPenyakit")

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

                            text_nama.text = obj1.getString("nama_penyakit")
                            val kategori = obj1.getString("kategori_penyakit")
                            text_kategori.text = "Kategori : $kategori"
                            text_deskripsi.text = obj1.getString("deskripsi")
                            text_penyebab.text = obj1.getString("penyebab")
                            text_gejala.text = obj1.getString("gejala")
                            text_pencegahan.text = obj1.getString("pencegahan")
                            text_pengobatan.text = obj1.getString("pengobatan")
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
