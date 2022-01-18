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
import com.example.trab_final.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.*
import kotlin.coroutines.*


class Login : AppCompatActivity() {

    private lateinit var inputEmail : EditText
    private lateinit var inputPassword : EditText
    private lateinit var signupBtn : Button
    private lateinit var loginBtn : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var database : DatabaseReference
    private var _role : String? = null


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




    private fun getRole(){
            database.child("user").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (postSnapshop in snapshot.children) {
                        var currentUser = postSnapshop.getValue(User::class.java)
                        if (mAuth.currentUser?.uid == currentUser?.uId) {
                            _role = currentUser?.role.toString()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
    private fun enterPage(role : String?){
        if(role.equals("empresa")){
            val intent = Intent(this@Login, MainEmpresa::class.java)
            startActivity(intent)
        }else if(role.equals("motoqueiro")){
            val intent = Intent(this@Login, MainEmpresa::class.java)
            startActivity(intent)
        }else if(role.equals("atendente")){
            val intent = Intent(this@Login, funcionarioListOrders::class.java)
            startActivity(intent)
        }
        finish()

    }
    private fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    database = FirebaseDatabase.getInstance().getReference()

                    CoroutineScope(Dispatchers.IO).launch {
                        val res: Deferred<Unit> = async { getRole() }
                        res.await()
                        withContext(Dispatchers.Main){
                            _role?.let {
                                println(_role)
                                enterPage(_role)
                            }
                        }
                    }

                }else{
                    Toast.makeText(this@Login, "User does not exist", Toast.LENGTH_LONG).show()
                }
            }
    }
}