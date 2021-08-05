package com.rifqiiardhian.bismillahinibisa

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import org.w3c.dom.Text
import java.util.*

class userhome : Fragment(), View.OnClickListener {

    var view1: View? = null
    var carousel_image = intArrayOf(
        R.drawable.mainslide1,
        R.drawable.mainslide2,
        R.drawable.mainslide3,
        R.drawable.mainslide4
    )
    var session: SessionManager? = null

    private lateinit var nama: TextView
    private lateinit var nim: TextView

    private lateinit var btn_penyakit: Button
    private lateinit var btn_obat: Button
    private lateinit var btn_dokter: Button
    private lateinit var btn_jadwal: Button
    private lateinit var btn_antrian: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_userhome, container, false)

        session = SessionManager(context!!)
        val user: HashMap<String, String?> = session!!.dataUser

        val nama_depan = user[SessionManager.NAMA_DEPAN]
        val nama_belakang = user[SessionManager.NAMA_BELAKANG]
        val nim_user = user[SessionManager.NIM_USER]

        nama = view1!!.findViewById(R.id.display_name)
        nama.text = "$nama_depan $nama_belakang"

        nim = view1!!.findViewById(R.id.display_nim)
        nim.text = nim_user

        val carousel = view1!!.findViewById<View>(R.id.carousel_dashboard) as CarouselView

        var imageListener: ImageListener =
            ImageListener { position, imageView -> imageView.setImageResource(carousel_image[position])
            }

        carousel.pageCount = carousel_image.size;
        carousel.setImageListener(imageListener)

        btn_penyakit = view1!!.findViewById(R.id.menu_penyakit)
        btn_penyakit.setOnClickListener(this)

        btn_obat = view1!!.findViewById(R.id.menu_obat)
        btn_obat.setOnClickListener(this)

        btn_dokter = view1!!.findViewById(R.id.menu_dokter)
        btn_dokter.setOnClickListener(this)

        btn_jadwal = view1!!.findViewById(R.id.menu_jadwal)
        btn_jadwal.setOnClickListener(this)

        btn_antrian = view1!!.findViewById(R.id.menu_antrian)
        btn_antrian.setOnClickListener(this)

        return view1
    }

    companion object {
        fun newInstance(): userhome {
            return userhome()
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.menu_penyakit) {
            val intent = Intent(context, penyakit::class.java)
            startActivity(intent)
        } else if (view.id == R.id.menu_obat) {
            val intent = Intent(context, obat::class.java)
            startActivity(intent)
        } else if (view.id == R.id.menu_dokter) {
            val intent = Intent(context, dokter::class.java)
            startActivity(intent)
        } else if (view.id == R.id.menu_jadwal) {
            val intent = Intent(context, jadwal::class.java)
            startActivity(intent)
        } else if (view.id == R.id.menu_antrian) {
            val intent = Intent(context, antrian::class.java)
            startActivity(intent)
        }
    }
}
