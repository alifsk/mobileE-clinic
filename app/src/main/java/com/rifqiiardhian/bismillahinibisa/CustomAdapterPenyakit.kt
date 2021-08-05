package com.rifqiiardhian.bismillahinibisa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*

class CustomAdapterPenyakit( private val context: Context, private val daftar_penyakit: ArrayList <model_penyakit> ): BaseAdapter() {

    private val inflater: LayoutInflater  = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(i: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.list_penyakit, parent, false)

        val nama_penyakit = view.findViewById<View>(R.id.nama_penyakit) as TextView
        val kategori_penyakit = view.findViewById<View>(R.id.kategori_penyakit) as TextView

        val penyakit = daftar_penyakit[i]

        nama_penyakit.text = penyakit.nama_penyakit
        kategori_penyakit.text = penyakit.kategori_penyakit

        return view
    }

    override fun getItem(i: Int): Any {
        return daftar_penyakit[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return daftar_penyakit.size
    }
}