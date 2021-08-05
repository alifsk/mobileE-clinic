package com.rifqiiardhian.bismillahinibisa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.ArrayList

class CustomAdapterObat(private val context: Context, private val daftar_obat: ArrayList<model_obat>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(i: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.list_obat, parent, false)
        val nama_obat = view.findViewById<View>(R.id.nama_obat) as TextView

        val obat = daftar_obat[i]
        nama_obat.text = obat.nama_obat

        return view
    }

    override fun getItem(i: Int): Any {
        return daftar_obat[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return daftar_obat.size
    }

}