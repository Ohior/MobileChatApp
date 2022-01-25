package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class LogIn : AppCompatActivity() {
    private lateinit var btn_signup: Button
    private lateinit var btn_log_in: Button
    private lateinit var email_et: EditText
    private lateinit var password_et: EditText
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        //to not display the app name
        supportActionBar?.hide()

        //how to init firebase auth
        mAuth = FirebaseAuth.getInstance()
        email_et = findViewById(R.id.edt_email)
        password_et = findViewById(R.id.edt_password)

        loadSignUpActivity()
        loadLoginActivity()
    }

    private fun loadLoginActivity() {
        btn_log_in = findViewById(R.id.btnLogin)
        btn_log_in.setOnClickListener {
            val email = email_et.text.toString()
            val password = password_et.text.toString()

            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        //logic for user login
        mAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                //code for logging out user
                val intent = Intent(this@LogIn, MainActivity::class.java)
                //finish this activity
                finish()
                startActivity(intent)
            } else {
                Toast.makeText(this@LogIn, "User does not exist", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadSignUpActivity() {
        btn_signup = findViewById(R.id.btnSignUp)
        btn_signup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}