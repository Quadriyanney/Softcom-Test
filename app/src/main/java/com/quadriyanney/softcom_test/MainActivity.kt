package com.quadriyanney.softcom_test

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var form: Form

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonString = loadJSONFromAsset()

        if (jsonString != null) {
            form = Gson().fromJson(jsonString, Form::class.java)

            tvFormTitle.text = form.name

            val fragments = arrayListOf<Fragment>()

            for (page in form.pages) {
                fragments.add(PageFragment.newInstance(page))
            }

            val pageAdapter = PageAdapter(fragments, supportFragmentManager)
            viewPager.offscreenPageLimit = fragments.size
            viewPager.adapter = pageAdapter

            fabNextPage.setOnClickListener {
                pageAdapter.getItem(viewPager.currentItem++)
                updatePageUI()
            }

            fabPreviousPage.setOnClickListener {
                pageAdapter.getItem(viewPager.currentItem--)
                updatePageUI()
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun updatePageUI() {
        when (viewPager.currentItem) {
            0 -> {
                fabNextPage.visibility = View.VISIBLE
                fabPreviousPage.visibility = View.INVISIBLE
                fabSubmit.visibility = View.INVISIBLE
            }
            form.pages.lastIndex -> {
                fabNextPage.visibility = View.INVISIBLE
                fabPreviousPage.visibility = View.VISIBLE
                fabSubmit.visibility = View.VISIBLE
            }
            else -> {
                fabNextPage.visibility = View.VISIBLE
                fabPreviousPage.visibility = View.VISIBLE
                fabSubmit.visibility = View.INVISIBLE
            }
        }
    }

    private fun loadJSONFromAsset(): String? {
        return try {
            val jsonFile = assets.open("pet_adoption-1.json.json")
            val size = jsonFile.available()
            val buffer = ByteArray(size)

            jsonFile.read(buffer)
            jsonFile.close()

            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            null
        }
    }
}
