package com.rifqiiardhian.bismillahinibisa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var carousel_image = intArrayOf(
        R.drawable.sliderutama,
        R.drawable.slider1,
        R.drawable.slider2,
        R.drawable.slider3
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val carousel = findViewById<View>(R.id.carousel_landing) as CarouselView

        var imageListener: ImageListener =
            ImageListener { position, imageView -> imageView.setImageResource(carousel_image[position])
            }

        carousel.pageCount = carousel_image.size;
        carousel.setImageListener(imageListener)

        val btn_login = findViewById<View>(R.id.btn_landing_login) as Button
        val btn_register = findViewById<View>(R.id.btn_landing_register) as Button
        btn_login.setOnClickListener(this)
        btn_register.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if(view.id == R.id.btn_landing_login) {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        } else if (view.id == R.id.btn_landing_register) {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
            finish()
        }
    }
}
