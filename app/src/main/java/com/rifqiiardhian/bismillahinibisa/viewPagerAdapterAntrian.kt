package com.rifqiiardhian.bismillahinibisa

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class viewPagerAdapterAntrian(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val pages = listOf(
        antrianterbaru(),
        riwayatantrian()
    )
    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Terbaru"
            else -> "Riwayat"
        }
    }
}