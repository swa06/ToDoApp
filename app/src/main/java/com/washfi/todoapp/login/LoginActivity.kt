package com.washfi.todoapp.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.washfi.todoapp.utils.AppConstant
import com.washfi.todoapp.data.local.pref.PrefConstant
import com.washfi.todoapp.R
import com.washfi.todoapp.data.local.pref.StoreSession
import com.washfi.todoapp.mynotes.MyNotesActivity

class LoginActivity : AppCompatActivity() {
    lateinit var editTextFullName: EditText
    lateinit var editTextUserName: EditText
    lateinit var buttonLogin:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindView()
        setUpSharedPreferences()
    }

    private fun setUpSharedPreferences() {
        StoreSession.init(this)
    }

    private fun bindView() {
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUserName = findViewById(R.id.editTextUserName)
        buttonLogin = findViewById(R.id.buttonLogin)
        val clickAction = object  : View.OnClickListener {
            override fun onClick(p0: View?) {
                val fullName = editTextFullName.text.toString()
                val userName = editTextUserName.text.toString()
                if( fullName.isNotEmpty() && userName.isNotEmpty()) {
                    val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                    intent.putExtra(AppConstant.FULL_NAME, fullName)
                    startActivity(intent)
                    saveFullName(fullName)
                    saveLoginStatus()
                } else{
                    Toast.makeText(this@LoginActivity, "Full name and user name can't " +
                            "be empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
        buttonLogin.setOnClickListener(clickAction)
    }

    private fun saveFullName(fullName: String) {
        StoreSession.write(PrefConstant.FULL_NAME, fullName)
    }

    private fun saveLoginStatus() {
        StoreSession.write(PrefConstant.IS_LOGGED_IN, true)
    }
}