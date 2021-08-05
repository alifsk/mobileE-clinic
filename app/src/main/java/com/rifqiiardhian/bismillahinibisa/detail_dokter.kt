package com.rifqiiardhian.bismillahinibisa

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import org.json.JSONObject

class detail_dokter : AppCompatActivity(), View.OnClickListener {

    private lateinit var back: Button
    private var id_dokter_detail: String? = null
    private var ID_DOKTER_KOFIRMASI = "id_dokter_konfirm"
    private val url = "http://eclinic.rifqiiardhian.my.id/api/Dokterapi.php"

    private lateinit var gambar_dokter: ImageView
    private lateinit var nama_dokter: TextView
    private lateinit var nama_poli: TextView
    private lateinit var jadwal_praktik: TextView
    private lateinit var jenis_kelamin: TextView
    private lateinit var temui_dokter: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_dokter)

        val intent = intent
        id_dokter_detail = intent.getStringExtra("id_dokter_detail")

        gambar_dokter = findViewById(R.id.dokter_image)
        nama_dokter = findViewById(R.id.nama_dokter)
        nama_poli = findViewById(R.id.poli)
        jadwal_praktik = findViewById(R.id.jadwal)
        jenis_kelamin = findViewById(R.id.gender)

        back = findViewById(R.id.back)
        back.setOnClickListener(this)

        temui_dokter = findViewById(R.id.temui_dokter)
        temui_dokter.setOnClickListener(this)

        loaddatadetail()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.back) {
            finish()
        } else if (view.id == R.id.temui_dokter) {
            val intent = Intent(applicationContext, konfirmasi_antrian::class.java)
            intent.putExtra(ID_DOKTER_KOFIRMASI, id_dokter_detail)
            startActivity(intent)
        }
    }

    fun loaddatadetail() { RequestAsyncDokterDetail().execute() }

    inner class RequestAsyncDokterDetail: AsyncTask<String?, String?, String?>() {
        var progress_load: RelativeLayout? = null
        var background_load: RelativeLayout? = null

        override fun onPreExecute() {
            super.onPreExecute()

            background_load = findViewById(R.id.backgroud_loading_dokter)
            background_load!!.visibility = View.VISIBLE

            progress_load = findViewById(R.id.progress_loading_dokter)
            progress_load!!.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg strngs: String?): String? {
            return try {
                // POST Request
                val postDataParams = JSONObject()
                postDataParams.put("id_dokter", id_dokter_detail)
                postDataParams.put("method", "DetailDokter")

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

                            val namadokter = obj1.getString("nama_dokter")
                            val gelar_depan = obj1.getString("gelar_depan")
                            val gelar_belakang = obj1.getString("gelar_belakang")
                            val genderdokter = obj1.getString("gender")
                            val polidokter = obj1.getString("poli")
                            val haripraktik = obj1.getString("hari_praktik")
                            val jampraktik = obj1.getString("jam_praktik")


                            nama_dokter.text = "$gelar_depan. $namadokter" +if(gelar_belakang != "null"){ ", $gelar_belakang." } else { "" }+""

                            if (genderdokter == "0") {
                                gambar_dokter.setImageResource(R.drawable.cowo)
                            } else if (genderdokter == "1") {
                                gambar_dokter.setImageResource(R.drawable.cewe)
                            }

                            nama_poli.text = "Poli $polidokter"

                            if ( haripraktik == "1") {
                                jadwal_praktik.text = "Senin - $jampraktik"
                            } else if ( haripraktik == "2" ) {
                                jadwal_praktik.text = "Selasa - $jampraktik"
                            } else if ( haripraktik == "3" ) {
                                jadwal_praktik.text = "Rabu - $jampraktik"
                            } else if ( haripraktik == "4" ) {
                                jadwal_praktik.text = "Kamis - $jampraktik"
                            } else if ( haripraktik == "5" ) {
                                jadwal_praktik.text = "Jumat - $jampraktik"
                            } else if ( haripraktik == "6" ) {
                                jadwal_praktik.text = "Sabtu - $jampraktik"
                            } else if ( haripraktik == "7" ) {
                                jadwal_praktik.text = "Minggu - $jampraktik"
                            }
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
