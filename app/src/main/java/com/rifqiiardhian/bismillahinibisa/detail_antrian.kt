package com.rifqiiardhian.bismillahinibisa

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject

class detail_antrian : AppCompatActivity(), View.OnClickListener {

    private lateinit var back: ImageView
    private var id_detail_antrian: String? = null
    private lateinit var no_antrian: TextView
    private lateinit var tanggal: TextView
    private lateinit var statusantrian: TextView
    private lateinit var nim: TextView
    private lateinit var nama_user: TextView
    private lateinit var nama_dokter: TextView
    private lateinit var poli: TextView
    private lateinit var jadwal_praktik: TextView
    private lateinit var pending_note: TextView

    private val url = "http://eclinic.rifqiiardhian.my.id/api/Antrianapi.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_antrian)

        val intent = intent
        id_detail_antrian = intent.getStringExtra("id_detail_antrian")

        no_antrian = findViewById(R.id.no_antrian)
        statusantrian = findViewById(R.id.status)
        tanggal = findViewById(R.id.tanggal)
        nim = findViewById(R.id.nim)
        nama_user = findViewById(R.id.nama)
        nama_dokter = findViewById(R.id.nama_dokter)
        poli = findViewById(R.id.poli)
        jadwal_praktik = findViewById(R.id.jadwal_praktik)
        pending_note = findViewById(R.id.antrian_pending_note)

        loaddatadetail()

        back = findViewById(R.id.back)
        back.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.back) {
            finish()
        }
    }

    fun loaddatadetail() { RequestAsyncDetailAntrian().execute() }

    inner class RequestAsyncDetailAntrian: AsyncTask<String?, String?, String?>() {
        var progress_load: RelativeLayout? = null
        var background_load: RelativeLayout? = null

        override fun onPreExecute() {
            super.onPreExecute()

            background_load = findViewById(R.id.backgroud_loading)
            background_load!!.visibility = View.VISIBLE

            progress_load = findViewById(R.id.progress_loading)
            progress_load!!.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg strings: String?): String? {
            return try {
                // POST Request
                val postDataParams = JSONObject()
                postDataParams.put("id_antrian", id_detail_antrian)
                postDataParams.put("method", "DetailAntrian")

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

                            val noantrian = obj1.getString("no_antrian")
                            val sttus = obj1.getString("status")
                            val tanggalberobat = obj1.getString("tanggal")
                            val nimuser = obj1.getString("nim")
                            val namadepan = obj1.getString("nama_depan")
                            val namabelakang = obj1.getString("nama_belakang")
                            val gelardepan = obj1.getString("gelar_depan")
                            val gelarbelakang = obj1.getString("gelar_belakang")
                            val namadokter = obj1.getString("nama_dokter")
                            val polidokter = obj1.getString("poli")
                            val haripraktik = obj1.getString("hari")
                            val jampraktik = obj1.getString("jam")

                            statusantrian.text = sttus
                            statusantrian.isAllCaps = true

                            if (sttus == "success") {
                                no_antrian.text = "No. $noantrian"
                                statusantrian.setTextColor(Color.GREEN)
                                pending_note.visibility = View.GONE
                            } else {
                                statusantrian.setTextColor(Color.RED)
                                pending_note.visibility = View.VISIBLE
                            }

                            tanggal.text = tanggalberobat
                            nim.text = nimuser
                            nama_user.text = "$namadepan $namabelakang"

                            nama_dokter.text = "$gelardepan. $namadokter" +if(gelarbelakang != "null"){ ", $gelarbelakang." } else { "" }+""
                            poli.text = polidokter
                            when (haripraktik) {
                                "1" -> {
                                    jadwal_praktik.text = "Senin - $jampraktik"
                                }
                                "2" -> {
                                    jadwal_praktik.text = "Selasa - $jampraktik"
                                }
                                "3" -> {
                                    jadwal_praktik.text = "Rabu - $jampraktik"
                                }
                                "4" -> {
                                    jadwal_praktik.text = "Kamis - $jampraktik"
                                }
                                "5" -> {
                                    jadwal_praktik.text = "Jumat - $jampraktik"
                                }
                                "6" -> {
                                    jadwal_praktik.text = "Sabtu - $jampraktik"
                                }
                                "7" -> {
                                    jadwal_praktik.text = "Minggu - $jampraktik"
                                }
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
