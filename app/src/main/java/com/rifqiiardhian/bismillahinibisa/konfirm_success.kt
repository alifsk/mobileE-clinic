package com.rifqiiardhian.bismillahinibisa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class konfirm_success : AppCompatActivity(), View.OnClickListener {

    private lateinit var back: Button
    private lateinit var cek: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirm_success)

        back = findViewById(R.id.back)

        back.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.back) {
            val intent = Intent(applicationContext, mainuser::class.java)
            startActivity(intent)
            finish()
        }
    }
}
