package com.example.tabsfragments

import android.content.res.Resources
import android.os.Bundle
import android.widget.TableLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var tableLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tableLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = PageAdapter(this)
        TabLayoutMediator(tableLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> { "Primera"}
                1 -> { "Segunda"}
                2 -> { "Tercera"}
                else -> {throw Resources.NotFoundException("Position not found")}
            }
        }.attach()

    }
}