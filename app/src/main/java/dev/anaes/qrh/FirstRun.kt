package dev.anaes.qrh

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_firstrun.*
import kotlin.system.exitProcess


class FirstRun : AppCompatActivity(), ViewTreeObserver.OnScrollChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref: SharedPreferences = getSharedPreferences("dev.anaes.qrh", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

            setContentView(R.layout.activity_firstrun)

            val buttonExit = findViewById<Button>(R.id.button_exit)
            val buttonAgreeInactive = findViewById<Button>(R.id.button_agree_inactive)
            val buttonAgreeActive = findViewById<Button>(R.id.button_agree_active)
            val scrollViewSplash = findViewById<ScrollView>(R.id.splash_scroll)

            scrollViewSplash.viewTreeObserver.addOnScrollChangedListener(this)

            val verCode = BuildConfig.VERSION_NAME
            val verOutput = "Version $verCode"
            findViewById<TextView>(R.id.about_version).text = verOutput

            //CC license link

            val scrollBounds = Rect()
            scrollViewSplash.getHitRect(scrollBounds)
            if (scrolledToBottom.getLocalVisibleRect(scrollBounds)) {
                buttonAgreeActive.visibility = View.VISIBLE
                buttonAgreeInactive.visibility = View.GONE
                buttonAgreeActive.isClickable = true
                buttonAgreeInactive.isClickable = false
            }

            buttonExit.setOnClickListener {
                exitProcess(1)
            }

            buttonAgreeInactive.setOnClickListener {
                Snackbar.make(findViewById(android.R.id.content), "Please scroll and read before continuing.", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.snackbarBackground))
                    .setAction("Dismiss") {
                    }
                    .show()
            }

            buttonAgreeActive.setOnClickListener {
                editor.putBoolean("seen_warning", true)
                editor.apply()
                startActivity(Intent(this, Main::class.java))
                finish()
            }

            //CC license link
            imageViewCC.setOnClickListener{
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.creativecommons.org/licenses/by-nc-sa/4.0/")))
            }

        }


    override fun onScrollChanged() {
        val scrollViewSplash: ScrollView = findViewById(R.id.splash_scroll)
        val scrollBounds = Rect()
        scrollViewSplash.getHitRect(scrollBounds)

        //Two methods to check if scrolled to bottom for redundancy (as one does not work reliably on tablet screen where all visible from start)

        if (scrolledToBottom.getLocalVisibleRect(scrollBounds) || !scrollViewSplash.canScrollVertically(1)) {
            findViewById<Button>(R.id.button_agree_active).visibility = View.VISIBLE
            findViewById<Button>(R.id.button_agree_inactive).visibility = View.GONE
            button_agree_active.isClickable = true
            button_agree_inactive.isClickable = false
        }
    }
}