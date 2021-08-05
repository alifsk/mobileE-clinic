package com.rifqiiardhian.bismillahinibisa

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.util.HashMap

class userprofile : Fragment(), View.OnClickListener {

    var view1: View? = null
    var session: SessionManager? = null

    private lateinit var nama: TextView
    private lateinit var nim: TextView
    private lateinit var email: TextView

    private lateinit var edit_profil: Button
    private lateinit var btn_edit_password: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_userprofile, container, false)

        session = SessionManager(context!!)
        val user: HashMap<String, String?> = session!!.dataUser

        nama = view1!!.findViewById(R.id.nama)
        nim = view1!!.findViewById(R.id.nim)
        email = view1!!.findViewById(R.id.email)
        edit_profil = view1!!.findViewById(R.id.edit_profil)
        btn_edit_password = view1!!.findViewById(R.id.btn_edit_password)

        val namadepan = user[SessionManager.NAMA_DEPAN]
        val namabelakang = user[SessionManager.NAMA_BELAKANG]
        val nimuser = user[SessionManager.NIM_USER]
        val emailuser = user[SessionManager.EMAIL_USER]

        nama.text = "$namadepan $namabelakang"
        nim.text = nimuser
        email.text = emailuser

        edit_profil.setOnClickListener(this)
        btn_edit_password.setOnClickListener(this)

        return view1
    }

    companion object {
        fun newInstance(): userprofile {
            return userprofile()
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.edit_profil) {
            val intent = Intent(context, edit_profile::class.java)
            startActivity(intent)
        } else if (view.id == R.id.btn_edit_password) {
            val intent = Intent(context, edit_password::class.java)
            startActivity(intent)
        }
    }
}
