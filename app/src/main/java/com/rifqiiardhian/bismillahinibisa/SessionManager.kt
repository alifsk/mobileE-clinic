package com.rifqiiardhian.bismillahinibisa

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import java.util.HashMap

class SessionManager(var context: Context) {

    var pref: SharedPreferences
    var editor: Editor
    var PRIVATE_MODE = 0

    fun createLoginSession(id_user: String?, nama_depan: String?, nama_belakang: String?, nim_user: String?, email_user: String?, pass_user: String?) {
        editor.putString(ID_USER, id_user)
        editor.putString(NAMA_DEPAN, nama_depan)
        editor.putString(NAMA_BELAKANG, nama_belakang)
        editor.putString(NIM_USER, nim_user)
        editor.putString(EMAIL_USER, email_user)
        editor.putString(PASS_USER, pass_user)
        editor.commit()
    }

    val dataUser: HashMap<String, String?>
        get() {
            val user = HashMap<String, String?>()
            user[ID_USER] = pref.getString(ID_USER, null)
            user[NAMA_DEPAN] = pref.getString(NAMA_DEPAN, null)
            user[NAMA_BELAKANG] = pref.getString(NAMA_BELAKANG, null)
            user[NIM_USER] = pref.getString(NIM_USER, null)
            user[EMAIL_USER] = pref.getString(EMAIL_USER, null)
            user[PASS_USER] = pref.getString(PASS_USER, null)
            return user
        }

    fun clearSession() {
        editor.clear().commit()
    }

    companion object {
        private const val PREF_NAME = "User References"
        const val ID_USER = "id_user"
        const val NAMA_DEPAN = "nama_depan"
        const val NAMA_BELAKANG = "nama_belakang"
        const val NIM_USER = "nim"
        const val EMAIL_USER = "email"
        const val PASS_USER = "password"
    }

    init {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

}