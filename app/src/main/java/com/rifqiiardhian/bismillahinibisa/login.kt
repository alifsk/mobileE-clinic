package com.rifqiiardhian.bismillahinibisa

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class login : AppCompatActivity(), View.OnClickListener {

    private lateinit var usernim: TextInputEditText
    private lateinit var password: TextInputEditText
    var session: SessionManager? = null
    val data_login = HashMap<String, String>()
    private val url = "http://eclinic.rifqiiardhian.my.id/api/Authapi.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        session = SessionManager(applicationContext)

        usernim = findViewById(R.id.input_nim)
        password = findViewById(R.id.input_password)

        val btn_login = findViewById<View>(R.id.btn_login) as Button
        val btn_register = findViewById<View>(R.id.btn_register) as Button

        btn_login.setOnClickListener(this)
        btn_register.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_login) {

            val text_usernim: String = usernim.text.toString()
            val text_password: String = password.text.toString()

            if ( text_usernim.trim().isNotEmpty() && text_password.trim().isNotEmpty()) {
                data_login["usernim"] = text_usernim
                data_login["password"] = text_password
                RequestAsyncLogin().execute()
            } else {
                Toast.makeText(applicationContext, "NIM / Email dan Password Harus Diisi !", Toast.LENGTH_SHORT).show()
            }

        } else if(view.id == R.id.btn_register) {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
    }

    inner class RequestAsyncLogin : AsyncTask<String?, String?, String?>() {
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

                postDataParams.put("nimemail", data_login["usernim"])
                postDataParams.put("password", data_login["password"])
                postDataParams.put("method", "LoginRequest")

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

                    if (success == 1) {
                        val jArray =obj.getJSONArray("data")

                        for (i in 0 until jArray.length()) {
                            val obj1 = jArray.getJSONObject(i)

                            val id_user = obj1.getString("id_user")
                            val nama_depan = obj1.getString("nama_depan")
                            val nama_belakang = obj1.getString("nama_belakang")
                            val nim = obj1.getString("nim")
                            val email = obj1.getString("email")
                            val pass = obj1.getString("password")

                            session!!.createLoginSession(id_user, nama_depan, nama_belakang, nim, email, pass)
                            Toast.makeText(applicationContext, "Selamat Datang, $nama_depan !", Toast.LENGTH_LONG).show()
                            val intent = Intent(applicationContext, mainuser::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        val messages: String = obj.getString("message")
                        Toast.makeText(applicationContext, messages, Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    //Log.d("EX", e.toString());
                    Toast.makeText(applicationContext, "HALO $e", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}