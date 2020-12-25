package com.washfi.todoapp.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.washfi.todoapp.R
import com.washfi.todoapp.activity.LoginActivity
import com.washfi.todoapp.data.local.pref.PrefConstant
import com.washfi.todoapp.data.local.pref.PrefConstant.ON_BOARDED_SUCCESSFULLY

class OnboardingActivity : AppCompatActivity(), OnBoardingOneFragment.OnNextClick, OnBoardingTwoFragment.OnOptionClick {
    lateinit var viewPager: ViewPager
    private lateinit var sharedPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        bindView()
        setUpSharedPreferences()
    }

    private fun setUpSharedPreferences() {
        sharedPreference = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME,
                Context.MODE_PRIVATE)
    }

    private fun bindView() {
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    override fun onClick() {
        viewPager.currentItem = 1
    }

    override fun onOptionBack() {
        viewPager.currentItem = 0
    }

    override fun onOptionDone() {
        saveOnBoardingStatus()
        val intent = Intent(this@OnboardingActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun saveOnBoardingStatus() {
        editor = sharedPreference.edit()
        editor.putBoolean(ON_BOARDED_SUCCESSFULLY, true)
        editor.apply()
    }
}