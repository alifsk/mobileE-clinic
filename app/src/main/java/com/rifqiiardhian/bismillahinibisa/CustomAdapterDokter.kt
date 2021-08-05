package com.rifqiiardhian.bismillahinibisa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class CustomAdapterDokter(var context: Context, private val daftar_dokter: ArrayList<model_dokter>, onDokterListener: OnDokterListener): RecyclerView.Adapter<CustomAdapterDokter.MyViewHolder>() {

    private val mOnDokterListener: OnDokterListener = onDokterListener
    private var gelarbelakang: String? = null

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.card_dokter, parent, false)

        return MyViewHolder(v, mOnDokterListener)
    }

    override fun getItemCount(): Int {
        return daftar_dokter.size
    }

    override fun onBindViewHolder(holder: CustomAdapterDokter.MyViewHolder, position: Int) {
        val dokter = daftar_dokter[position]

        holder.nama_dokter.text = "${dokter.gelar_depan}. ${dokter.nama_dokter}"
        holder.poli_dokter.text = "Poli ${dokter.poli_dokter}"

        if ( dokter.hari_praktik == "1") {
            holder.jadwal_praktik.text = "Senin | ${dokter.jam_praktik}"
        } else if ( dokter.hari_praktik == "2" ) {
            holder.jadwal_praktik.text = "Selasa | ${dokter.jam_praktik}"
        } else if ( dokter.hari_praktik == "3" ) {
            holder.jadwal_praktik.text = "Rabu | ${dokter.jam_praktik}"
        } else if ( dokter.hari_praktik == "4" ) {
            holder.jadwal_praktik.text = "Kamis | ${dokter.jam_praktik}"
        } else if ( dokter.hari_praktik == "5" ) {
            holder.jadwal_praktik.text = "Jumat | ${dokter.jam_praktik}"
        } else if ( dokter.hari_praktik == "6" ) {
            holder.jadwal_praktik.text = "Sabtu | ${dokter.jam_praktik}"
        } else if ( dokter.hari_praktik == "7" ) {
            holder.jadwal_praktik.text = "Minggu | ${dokter.jam_praktik}"
        }

        if ( dokter.jenis_kelamin == "0" ) {
            holder.gambar_dokter.setImageResource(R.drawable.cowo)
        } else if ( dokter.jenis_kelamin == "1" ) {
            holder.gambar_dokter.setImageResource(R.drawable.cewe)
        }
    }

    inner class MyViewHolder(@NonNull itemView: View, onDokterListener: OnDokterListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var nama_dokter: TextView
        var poli_dokter: TextView
        var jadwal_praktik: TextView
        var gambar_dokter: ImageView
        var onDokterListener: OnDokterListener

        override fun onClick(view: View) {
            onDokterListener.onDokterClick(adapterPosition)
        }

        init {
            super.itemView

            nama_dokter = itemView.findViewById(R.id.nama_dokter)
            poli_dokter = itemView.findViewById(R.id.poli_dokter)
            jadwal_praktik = itemView.findViewById(R.id.jadwal_praktik)
            gambar_dokter = itemView.findViewById(R.id.gambar_dokter)
            this.onDokterListener = onDokterListener

            itemView.setOnClickListener(this)
        }
    }

    interface OnDokterListener {
        fun onDokterClick(position: Int)
    }

}