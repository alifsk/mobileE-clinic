package com.rifqiiardhian.bismillahinibisa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.ArrayList

class CustomAdapterJadwal(private val context: Context, private val daftar_jadwal: ArrayList<model_jadwal>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(i: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.list_jadwal, parent, false)
        val nama_dokter = view.findViewById<View>(R.id.nama_dokter) as TextView
        val poli = view.findViewById<View>(R.id.poli_dokter) as TextView
        val jam_praktik = view.findViewById<View>(R.id.jadwal_praktik) as TextView

        val jadwal = daftar_jadwal[i]

        if (jadwal.gelar_belakang == "null") {
            nama_dokter.text = "${jadwal.gelar_depan}. ${jadwal.nama_dokter}."
        } else {
            nama_dokter.text = "${jadwal.gelar_depan}. ${jadwal.nama_dokter}, ${jadwal.gelar_belakang}."
        }

        poli.text = "Poli ${jadwal.poli_dokter}"

        if ( jadwal.hari_praktik == "1") {
            jam_praktik.text = "Senin | ${jadwal.jam_praktik}"
        } else if ( jadwal.hari_praktik == "2" ) {
            jam_praktik.text = "Selasa | ${jadwal.jam_praktik}"
        } else if ( jadwal.hari_praktik == "3" ) {
            jam_praktik.text = "Rabu | ${jadwal.jam_praktik}"
        } else if ( jadwal.hari_praktik == "4" ) {
            jam_praktik.text = "Kamis | ${jadwal.jam_praktik}"
        } else if ( jadwal.hari_praktik == "5" ) {
            jam_praktik.text = "Jumat | ${jadwal.jam_praktik}"
        } else if ( jadwal.hari_praktik == "6" ) {
            jam_praktik.text = "Sabtu | ${jadwal.jam_praktik}"
        } else if ( jadwal.hari_praktik == "7" ) {
            jam_praktik.text = "Minggu | ${jadwal.jam_praktik}"
        }

        return view
    }

    override fun getItem(i: Int): Any {
        return daftar_jadwal[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return daftar_jadwal.size
    }
}