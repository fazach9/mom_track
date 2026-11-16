package com.example.momtrack

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Mewarnai ikon ic_back
        val backIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        if (backIcon != null) {
            DrawableCompat.setTint(backIcon, ContextCompat.getColor(this, R.color.white))
            supportActionBar?.setHomeAsUpIndicator(backIcon)
        }
        supportActionBar?.title = ""

        // Setup Content
        val gambar = intent.getIntExtra("GAMBAR", 0)
        val judul = intent.getStringExtra("JUDUL")
        val deskripsi = intent.getStringExtra("DESKRIPSI")

        findViewById<ImageView>(R.id.imgDetail).setImageResource(gambar)
        findViewById<TextView>(R.id.tvJudulDetail).text = judul
        findViewById<TextView>(R.id.tvDeskripsiDetail).text = deskripsi
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}