package com.rifqiiardhian.bismillahinibisa

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class penyakit : AppCompatActivity(), TextView.OnEditorActionListener, View.OnClickListener {

    private lateinit var back: ImageView
    private lateinit var search: EditText
    var KEYWORD_PENYAKIT = "keywordpenyakit";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_penyakit)

        back = findViewById(R.id.back)
        back.setOnClickListener(this)

        search = findViewById(R.id.input_search_penyakit)
        search.setOnEditorActionListener(this)

        if(savedInstanceState == null) {
            var frpenyakitall = penyakit_all.newInstance()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.framepenyakit, frpenyakitall)
            fragmentTransaction.commit()
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        var handled = false
        if ( actionId == EditorInfo.IME_ACTION_DONE ) {
            handled = true
            val hasil = search.text.toString()
            val frsearch = penyakit_search.newInstance()
            val bundle = Bundle()

            bundle.putString(KEYWORD_PENYAKIT, hasil)
            frsearch.arguments = bundle
            loadFragment(frsearch)

            search.setText("")
        }
        return handled
    }

    private fun loadFragment(fragment: Fragment) {
        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.framepenyakit, fragment)
        fragmentTransaction.commit()
    }

    override fun onClick(view: View) {
        if ( view.id == R.id.back ) {
            finish()
        }
    }
}
