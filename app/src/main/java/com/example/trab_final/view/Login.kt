package com.example.trab_final.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.trab_final.Main
import com.example.trab_final.R
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var inputEmail : EditText
    private lateinit var inputPassword : EditText
    private lateinit var signupBtn : Button
    private lateinit var loginBtn : Button
    private lateinit var mAuth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        inputEmail = findViewById(R.id.edit_email)
        inputPassword = findViewById(R.id.edit_password)
        signupBtn = findViewById(R.id.btn_signup)
        loginBtn = findViewById(R.id.btn_login)

        signupBtn.setOnClickListener{
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener{
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()
            login(email, password)
        }
    }

    private fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    val intent = Intent(this@Login, Main::class.java)
                    finish()
                    startActivity(intent)
                }else{
                    Toast.makeText(this@Login, "User does not exist", Toast.LENGTH_LONG).show()
                }
            }
    }
}