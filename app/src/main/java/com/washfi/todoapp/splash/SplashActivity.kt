package com.washfi.todoapp.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.washfi.todoapp.data.local.pref.PrefConstant
import com.washfi.todoapp.R
import com.washfi.todoapp.activity.LoginActivity
import com.washfi.todoapp.activity.MyNotesActivity
import com.washfi.todoapp.onboarding.OnboardingActivity
import com.washfi.todoapp.data.local.pref.StoreSession

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setUpSharedPreferences()
        checkLoginStatus()
        getFcmToken()
    }

    private fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("SplashActivity", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("SplashActivity", token.toString())
            Toast.makeText(baseContext, token.toString(), Toast.LENGTH_SHORT).show()
        })
    }

    private fun setUpSharedPreferences() {
        StoreSession.init(this)
    }

    private fun checkLoginStatus() {
        val isLoggedIn = StoreSession.read(PrefConstant.IS_LOGGED_IN)
        val isBoardingSuccessful = StoreSession.read(PrefConstant.ON_BOARDED_SUCCESSFULLY)

        if (isLoggedIn!!) {
            val intent = Intent(this@SplashActivity, MyNotesActivity::class.java)
            startActivity(intent)
        } else {
            if (isBoardingSuccessful!!) {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
                startActivity(intent)
            }
        }
        finish()
    }
}