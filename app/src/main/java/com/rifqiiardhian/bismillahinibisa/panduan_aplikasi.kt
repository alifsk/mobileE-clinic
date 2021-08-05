package com.rifqiiardhian.bismillahinibisa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class panduan_aplikasi : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_panduan_aplikasi, container, false)
    }

    companion object {
        fun newInstance(): panduan_aplikasi {
            return panduan_aplikasi()
        }
    }
}
