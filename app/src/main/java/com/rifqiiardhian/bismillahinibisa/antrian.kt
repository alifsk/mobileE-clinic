package com.rifqiiardhian.bismillahinibisa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_antrian.*

class antrian : AppCompatActivity(), View.OnClickListener {

    private lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_antrian)

        viewpager_antrian.adapter = viewPagerAdapterAntrian(supportFragmentManager)
        tabantrian.setupWithViewPager(viewpager_antrian)

        back = findViewById(R.id.back)
        back.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.back) {
            finish()
        }
    }
}
