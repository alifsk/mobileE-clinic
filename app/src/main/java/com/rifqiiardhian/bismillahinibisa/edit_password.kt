package com.rifqiiardhian.bismillahinibisa

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject
import java.lang.Exception
import java.util.HashMap

class edit_password : AppCompatActivity(), View.OnClickListener {

    var session: SessionManager? = null
    private val url = "http://eclinic.rifqiiardhian.my.id/api/Authapi.php"

    private lateinit var back: ImageView
    private lateinit var btn_simpan: Button

    private lateinit var text_password_lama: TextInputEditText
    private lateinit var text_password_baru: TextInputEditText
    private lateinit var text_konfirm_password: TextInputEditText

    private var iduser: String? = null
    private var pass_lama: String? = null
    private var password_lama: String? = null
    private var password_baru: String? = null
    private var konfirm_password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_password)

        back = findViewById(R.id.back)
        btn_simpan = findViewById(R.id.btn_simpan)
        text_password_lama = findViewById(R.id.input_password)
        text_password_baru = findViewById(R.id.input_password_baru)
        text_konfirm_password = findViewById(R.id.input_konfirm_password)

        session = SessionManager(applicationContext)
        val user: HashMap<String, String?> = session!!.dataUser

        iduser = user[SessionManager.ID_USER]
        pass_lama = user[SessionManager.PASS_USER]

        back.setOnClickListener(this)
        btn_simpan.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.back) {
            finish()
        } else if (view.id == R.id.btn_simpan) {
            password_lama = text_password_lama.text.toString()
            password_baru = text_password_baru.text.toString()
            konfirm_password = text_konfirm_password.text.toString()

            if (password_lama!!.trim().isEmpty() || password_baru!!.trim().isEmpty() || konfirm_password!!.trim().isEmpty()) {
                Toast.makeText(applicationContext, "Anda harus melengkapi data diatas !", Toast.LENGTH_SHORT).show()
            } else {
                if (password_lama != pass_lama) {
                    Toast.makeText(applicationContext, "Maaf password lama yang anda isikan tidak sesuai !", Toast.LENGTH_SHORT).show()
                } else {
                    if (password_baru != konfirm_password) {
                        Toast.makeText(applicationContext, "Maaf konfirmasi password tidak sesuai !", Toast.LENGTH_SHORT).show()
                    } else {
                        RequestAsnycUpdatePassword().execute()
                    }
                }
            }
        }
    }

    companion object {}

    inner class RequestAsnycUpdatePassword : AsyncTask<String?, String?, String?>() {
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
                postDataParams.put("password", konfirm_password)
                postDataParams.put("method", "UpdatePassword")

                session!!.clearSession()
                session!!.createLoginSession(iduser, SessionManager.NAMA_DEPAN, SessionManager.NAMA_BELAKANG, SessionManager.NIM_USER, SessionManager.EMAIL_USER, konfirm_password)

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
