package com.rifqiiardhian.bismillahinibisa

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorInt
import java.util.ArrayList

class CustomAdapterAntrian(private val context: Context, private val daftar_antrian: ArrayList<model_antrian>): BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(i: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.list_antrian, parent, false)
        val nama_dokter = view.findViewById<View>(R.id.nama_dokter) as TextView
        val poli = view.findViewById<View>(R.id.poli_dokter) as TextView
        val tanggal = view.findViewById<View>(R.id.tanggal) as TextView
        val status = view.findViewById<View>(R.id.status) as TextView

        val antrian = daftar_antrian[i]

        if (antrian.gelar_belakang == "null") {
            nama_dokter.text = "${antrian.gelar_depan}. ${antrian.nama_dokter}."
        } else {
            nama_dokter.text = "${antrian.gelar_depan}. ${antrian.nama_dokter}, ${antrian.gelar_belakang}."
        }

        poli.text = "Poli ${antrian.poli_dokter}"
        tanggal.text = "Tanggal Berobat : ${antrian.tanggal_antrian}"

        status.text = antrian.status_antrian
        status.isAllCaps = true
        if (antrian.status_antrian == "success") {
            status.setTextColor(Color.GREEN)
        } else  {
            status.setTextColor(Color.RED)
        }

        return view
    }

    override fun getItem(i: Int): Any {
        return daftar_antrian[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return daftar_antrian.size
    }
}