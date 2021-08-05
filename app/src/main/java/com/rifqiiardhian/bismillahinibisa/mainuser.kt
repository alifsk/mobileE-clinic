package com.rifqiiardhian.bismillahinibisa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView

class mainuser : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    ViewPager.OnPageChangeListener {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var viewPager: ViewPager
    private lateinit var prevMenuItem: MenuItem

    var session: SessionManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainuser)

        session = SessionManager(applicationContext)

        viewPager = findViewById(R.id.viewpager)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        viewPager.addOnPageChangeListener(this)
        setupViewPager(viewPager)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id:Int = item.itemId

        if (id == R.id.home) {
            viewPager.currentItem = 0
        } else if (id == R.id.profile) {
            viewPager.currentItem = 1
        } else if (id == R.id.panduan) {
            viewPager.currentItem = 2
        } else if (id == R.id.logout) {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            session!!.clearSession()
            finish()
            Toast.makeText(applicationContext, "Anda telah logout", Toast.LENGTH_SHORT).show()
        }

        return true
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        bottomNavigationView.menu.getItem(position).isChecked = true
        prevMenuItem = bottomNavigationView.menu.getItem(position)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = viewPagerAdapter(supportFragmentManager)
        val frHome = userhome.newInstance()
        val frProfile = userprofile.newInstance()
        val frPanduan = panduan_aplikasi.newInstance()

        adapter.addFragment(frHome)
        adapter.addFragment(frProfile)
        adapter.addFragment(frPanduan)
        viewPager.adapter = adapter
    }
}
