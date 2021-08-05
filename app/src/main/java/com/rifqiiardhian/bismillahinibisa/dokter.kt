package com.rifqiiardhian.bismillahinibisa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class dokter : AppCompatActivity(), View.OnClickListener, TextView.OnEditorActionListener {

    private lateinit var back: ImageView
    private lateinit var search: EditText
    var KEYWORD_DOKTER = "keyworddokter"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dokter)

        back = findViewById(R.id.back)
        back.setOnClickListener(this)

        search = findViewById(R.id.input_search_dokter)
        search.setOnEditorActionListener(this)

        if(savedInstanceState == null) {
            var frdokterall = dokter_all.newInstance()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.framedokter, frdokterall)
            fragmentTransaction.commit()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.framedokter, fragment)
        fragmentTransaction.commit()
    }

    override fun onClick(view: View) {
        if ( view.id == R.id.back ) {
            finish()
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        var handled = false
        if ( actionId == EditorInfo.IME_ACTION_DONE ) {
            handled = true
            val hasil = search.text.toString()

            val frsearch = dokter_search.newInstance()
            val bundle = Bundle()

            bundle.putString(KEYWORD_DOKTER, hasil)
            frsearch.arguments = bundle
            loadFragment(frsearch)

            search.setText("")
        }
        return handled
    }
}
