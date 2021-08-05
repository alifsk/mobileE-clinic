package com.rifqiiardhian.bismillahinibisa

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject
import java.lang.Exception
import java.util.HashMap

class edit_profile : AppCompatActivity(), View.OnClickListener {

    var session: SessionManager? = null
    private val url = "http://eclinic.rifqiiardhian.my.id/api/Authapi.php"

    private lateinit var back: ImageView
    private lateinit var btn_simpan: Button

    private lateinit var text_nama_depan: TextInputEditText
    private lateinit var text_nama_belakang: TextInputEditText
    private lateinit var text_nim: TextInputEditText
    private lateinit var text_email: TextInputEditText

    private var iduser: String? = null
    private var namadepan: String? = null
    private var namabelakang: String? = null
    private var nim: String? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        text_nama_depan = findViewById(R.id.input_namadepan)
        text_nama_belakang = findViewById(R.id.input_namabelakang)
        text_nim = findViewById(R.id.input_nim)
        text_email = findViewById(R.id.input_email)

        session = SessionManager(applicationContext)
        val user: HashMap<String, String?> = session!!.dataUser

        iduser = user[SessionManager.ID_USER]

        text_nama_depan.setText(user[SessionManager.NAMA_DEPAN])
        text_nama_belakang.setText(user[SessionManager.NAMA_BELAKANG])
        text_nim.setText(user[SessionManager.NIM_USER])
        text_email.setText(user[SessionManager.EMAIL_USER])

        back = findViewById(R.id.back)
        back.setOnClickListener(this)

        btn_simpan = findViewById(R.id.btn_simpan)
        btn_simpan.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.back) {
            finish()
        } else if (view.id == R.id.btn_simpan) {
            namadepan = text_nama_depan.text.toString()
            namabelakang = text_nama_belakang.text.toString()
            nim = text_nim.text.toString()
            email = text_email.text.toString()

            if ( namadepan!!.trim().isEmpty() || namabelakang!!.trim().isEmpty() || nim!!.trim().isEmpty() || email!!.trim().isEmpty()) {
                Toast.makeText(applicationContext, "Anda harus melengkapi data diatas !", Toast.LENGTH_SHORT).show()
            } else {
                RequestAsnycUpdateProfil().execute()
            }
        }
    }

    companion object {}

    inner class RequestAsnycUpdateProfil : AsyncTask<String?, String?, String?>() {
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
                val postDataParams = JSONObject()

                postDataParams.put("id_user", iduser)
                postDataParams.put("nama_depan", namadepan)
                postDataParams.put("nama_belakang", namabelakang)
                postDataParams.put("nim", nim)
                postDataParams.put("email", email)
                postDataParams.put("method", "UpdateProfileRequest")

                session!!.clearSession()
                session!!.createLoginSession(iduser, namadepan, namabelakang, nim, email, SessionManager.PASS_USER)

                RequestHandler.sendPost(url, postDataParams)
            } catch (e: Exception) {
                "Exception ini saya coba: " + e.message
            }
        }

        override fun onPostExecute(result: String?) {
            background_load!!.visibility = View.GONE
            progress_load!!.visibility = View.GONE

            if (result != null) {
                try {
                    val obj = JSONObject(result)
                    val success: Int = obj.getInt("success")
                    val messages: String = obj.getString("message")

                    if (success == 1) {
                        Toast.makeText(applicationContext, messages, Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(applicationContext, messages, Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "HALO $e", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
