package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUp : AppCompatActivity() {
    private lateinit var btn_signup: Button
    private lateinit var email_et: EditText
    private lateinit var name_et: EditText
    private lateinit var password_et: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //to not display the app name
        supportActionBar?.hide()
        
        //how to init firebase mAuth
        mAuth = FirebaseAuth.getInstance()

        name_et = findViewById(R.id.edt_name)
        password_et = findViewById(R.id.edt_password)
        email_et = findViewById(R.id.edt_email)
        btn_signup = findViewById(R.id.btnSignUp)

        signUpBtnClickListener()
    }

    private fun signUpBtnClickListener() {
        btn_signup.setOnClickListener{
            val name = name_et.text.toString()
            val email = email_et.text.toString()
            val password = password_et.text.toString()

            signUp(name, email, password)
        }
    }

    private fun signUp(name: String, email:String, password:String) {
        //logic of user signup
        mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                // code for jumping to home activity
                val intent = Intent(this@SignUp, MainActivity::class.java)
                //finish this activity
                finish()
                startActivity(intent)
            } else {
                Toast.makeText(this@SignUp, "Some error occurred"
                    , Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        //now lets dd data to it
        //create a user to hole other user and a unique id for every user
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}