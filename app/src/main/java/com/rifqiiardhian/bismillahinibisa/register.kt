package com.rifqiiardhian.bismillahinibisa

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class register : AppCompatActivity(), View.OnClickListener {

    private lateinit var nama_depan: TextInputEditText
    private lateinit var nama_belakang: TextInputEditText
    private lateinit var nim: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var konfirmasi_password: TextInputEditText

    private var text_namadepan: String? = null
    private var text_namabelakang: String? = null
    private var text_nim: String? = null
    private var text_email: String? = null
    private var text_password: String? = null

    private val url = "http://eclinic.rifqiiardhian.my.id/api/Authapi.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nama_depan = findViewById(R.id.input_namadepan)
        nama_belakang = findViewById(R.id.input_namabelakang)
        nim = findViewById(R.id.input_nim)
        email = findViewById(R.id.input_email)
        password = findViewById(R.id.input_password)
        konfirmasi_password = findViewById(R.id.input_konfirmasipassword)

        val btn_login = findViewById<View>(R.id.btn_login) as Button
        val btn_register = findViewById<View>(R.id.btn_register) as Button

        btn_login.setOnClickListener(this)
        btn_register.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_register) {
            text_namadepan = nama_depan.text.toString()
            text_namabelakang = nama_belakang.text.toString()
            text_nim = nim.text.toString()
            text_email = email.text.toString()
            text_password = konfirmasi_password.text.toString()

            if ( text_namadepan!!.trim().isEmpty() || text_namabelakang!!.trim().isEmpty() || text_nim!!.trim().isEmpty() || text_email!!.trim().isEmpty() || text_password!!.trim().isEmpty()) {
                Toast.makeText(applicationContext, "Anda harus melengkapi data diatas !", Toast.LENGTH_SHORT).show()
            } else {
                if ( password.text.toString() == konfirmasi_password.text.toString() ) {
                    RequestAsnycRegister().execute()
                } else {
                    Toast.makeText(applicationContext, "Input password tidak sesuai ! Periksa ulang password anda", Toast.LENGTH_SHORT).show()
                }
            }
        } else if(view.id == R.id.btn_login) {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {}

    inner class RequestAsnycRegister : AsyncTask<String?, String?, String?>() {
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

                postDataParams.put("nama_depan", text_namadepan)
                postDataParams.put("nama_belakang", text_namabelakang)
                postDataParams.put("nim", text_nim)
                postDataParams.put("email", text_email)
                postDataParams.put("password", text_password)
                postDataParams.put("method", "RegisterRequest")

                RequestHandler.sendPost(url, postDataParams)
            } catch (e: Exception) {
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

                        val intent = Intent(applicationContext, login::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, messages, Toast.LENGTH_LONG).show()

                        nama_depan.setText("")
                        nama_belakang.setText("")
                        nim.setText("")
                        email.setText("")
                        password.setText("")
                        konfirmasi_password.setText("")
                    }
                } catch (e: JSONException) {
                    Toast.makeText(applicationContext, "HALO $e", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
