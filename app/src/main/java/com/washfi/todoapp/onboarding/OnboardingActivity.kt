package com.washfi.todoapp.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.washfi.todoapp.R
import com.washfi.todoapp.login.LoginActivity
import com.washfi.todoapp.data.local.pref.PrefConstant.ON_BOARDED_SUCCESSFULLY
import com.washfi.todoapp.data.local.pref.StoreSession

class OnboardingActivity : AppCompatActivity(), OnBoardingOneFragment.OnNextClick, OnBoardingTwoFragment.OnOptionClick {
    companion object{
        private const val FIRST_ITEM = 0
        private const val SECOND_ITEM = 1
    }
    lateinit var viewPager: ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        bindView()
        setUpSharedPreferences()
    }

    private fun setUpSharedPreferences() {
        StoreSession.init(this)
    }

    private fun bindView() {
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdapter(this)
        viewPager.adapter = adapter
    }

    override fun onClick() {
        viewPager.currentItem = SECOND_ITEM
    }

    override fun onOptionBack() {
        viewPager.currentItem = FIRST_ITEM
    }

    override fun onOptionDone() {
        saveOnBoardingStatus()
        val intent = Intent(this@OnboardingActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun saveOnBoardingStatus() {
        StoreSession.write(ON_BOARDED_SUCCESSFULLY, true)
    }
}