package com.rifqiiardhian.bismillahinibisa

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class konfirmasi_antrian : AppCompatActivity(), View.OnClickListener {

    private lateinit var back: ImageView
    private lateinit var konfirmasi: Button
    var session: SessionManager? = null

    private lateinit var text_nim: TextView
    private lateinit var text_namauser: TextView
    private lateinit var text_namadokter: TextView
    private lateinit var text_poli: TextView
    private lateinit var text_jadwalpraktik: TextView
    private lateinit var btn_tanggal: ImageButton
    private lateinit var text_tanggal: TextView

    private var id_user: String? = null
    private var tanggal: String? = null
    private var id_dokter: String? = null
    private var statusantrian: String? = "pending"

    private val urldokter = "http://eclinic.rifqiiardhian.my.id/api/Dokterapi.php"
    private val urlantrian = "http://eclinic.rifqiiardhian.my.id/api/Antrianapi.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi_antrian)

        session = SessionManager(applicationContext)
        val user: HashMap<String, String?> = session!!.dataUser

        val intent = intent
        id_dokter = intent.getStringExtra("id_dokter_konfirm")

        text_nim = findViewById(R.id.nim)
        text_namauser = findViewById(R.id.nama)
        text_namadokter = findViewById(R.id.dokter)
        text_poli = findViewById(R.id.poli)
        text_jadwalpraktik = findViewById(R.id.jadwal_praktik)
        btn_tanggal = findViewById(R.id.getTanggal)
        text_tanggal = findViewById(R.id.tanggal_berobat)

        val namadepan = user[SessionManager.NAMA_DEPAN]
        val namabelakang = user[SessionManager.NAMA_BELAKANG]

        id_user = user[SessionManager.ID_USER]
        text_nim.text = user[SessionManager.NIM_USER]
        text_namauser.text = "$namadepan $namabelakang"

        loaddatadokter()

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        btn_tanggal.setOnClickListener {
            val datedialog = DatePickerDialog(this, OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val bulan = monthOfYear + 1
                tanggal = "$year-$bulan-$dayOfMonth"
                text_tanggal.text = tanggal
            }, year, month, day)
            datedialog.show()
        }

        back = findViewById(R.id.back)
        back.setOnClickListener(this)

        konfirmasi = findViewById(R.id.konfirmasi)
        konfirmasi.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.back) {
            finish()
        } else if (view.id == R.id.konfirmasi) {
            val txttanggal = text_tanggal.text.toString()
            if (txttanggal.trim().isEmpty()) {
                Toast.makeText(applicationContext, "Tanggal harus dipilih !", Toast.LENGTH_SHORT).show()
            } else {
                loadkonfirmasi()
            }
        }
    }

    fun loaddatadokter() { RequestAsyncDataDokter().execute() }
    fun loadkonfirmasi() { RequestAsyncKonfirmasiAntrian().execute() }

    inner class RequestAsyncDataDokter: AsyncTask<String?, String?, String?>() {
        var progress_load: RelativeLayout? = null
        var background_load: RelativeLayout? = null

        override fun onPreExecute() {
            super.onPreExecute()

            background_load = findViewById(R.id.backgroud_loading)
            background_load!!.visibility = View.VISIBLE

            progress_load = findViewById(R.id.progress_loading)
            progress_load!!.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg strngs: String?): String? {
            return try {
                // POST Request
                val postDataParams = JSONObject()
                postDataParams.put("id_dokter", id_dokter)
                postDataParams.put("method", "DetailDokter")

                RequestHandler.sendPost(urldokter, postDataParams)
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


                            text_namadokter.text = "$gelar_depan. $namadokter" +if(gelar_belakang != "null"){ ", $gelar_belakang." } else { "" }+""

                            text_poli.text = "Poli $polidokter"

                            if ( haripraktik == "1") {
                                text_jadwalpraktik.text = "Senin - $jampraktik"
                            } else if ( haripraktik == "2" ) {
                                text_jadwalpraktik.text = "Selasa - $jampraktik"
                            } else if ( haripraktik == "3" ) {
                                text_jadwalpraktik.text = "Rabu - $jampraktik"
                            } else if ( haripraktik == "4" ) {
                                text_jadwalpraktik.text = "Kamis - $jampraktik"
                            } else if ( haripraktik == "5" ) {
                                text_jadwalpraktik.text = "Jumat - $jampraktik"
                            } else if ( haripraktik == "6" ) {
                                text_jadwalpraktik.text = "Sabtu - $jampraktik"
                            } else if ( haripraktik == "7" ) {
                                text_jadwalpraktik.text = "Minggu - $jampraktik"
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

    inner class RequestAsyncKonfirmasiAntrian: AsyncTask<String?, String?, String?>() {
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

                postDataParams.put("tanggal", tanggal)
                postDataParams.put("id_user", id_user)
                postDataParams.put("id_dokter", id_dokter)
                postDataParams.put("status", statusantrian)
                postDataParams.put("method", "KonfirmasiAntrian")

                RequestHandler.sendPost(urlantrian, postDataParams)
            } catch (e: java.lang.Exception) {
                "Exception ini saya coba: " + e.message
            }
        }

        override fun onPostExecute(result: String?) {
            background_load!!.visibility = View.GONE
            progress_load!!.visibility = View.GONE

            if ( result != null) {
                try {
                    val obj = JSONObject(result)
                    val success: Int = obj.getInt("success")
                    val messages: String = obj.getString("message")

                    if ( success == 1 ) {
                        Toast.makeText(applicationContext, messages, Toast.LENGTH_LONG).show()

                        val intent = Intent(applicationContext, konfirm_success::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, messages, Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    Toast.makeText(applicationContext, "HALO $e", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
