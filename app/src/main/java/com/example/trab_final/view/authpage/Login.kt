package com.example.trab_final.view.authpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.trab_final.R
import com.example.trab_final.models.User
import com.example.trab_final.view.company.MainCompany
import com.example.trab_final.view.deliveryman.DeliveryListOrders
import com.example.trab_final.view.employee.EmployeeListOrders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Login : AppCompatActivity() {

    private lateinit var inputEmail : EditText
    private lateinit var inputPassword : EditText
    private lateinit var signupBtn : Button
    private lateinit var loginBtn : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var _userList : ArrayList<User>

    companion object{
        var companionUser: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        _userList = ArrayList<User>()

        mAuth = FirebaseAuth.getInstance()

        inputEmail = findViewById(R.id.edit_email)
        inputPassword = findViewById(R.id.edit_password)
        signupBtn = findViewById(R.id.btn_signup)
        loginBtn = findViewById(R.id.btn_login)
        database = FirebaseDatabase.getInstance().reference
        getRole()

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
                    _userList.add(currentUser!!)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun enterPage(currentId : String?){
        for ( user in _userList ){
            if(user.uId == currentId.toString()){
                companionUser = user
                if(user.role.equals("empresa")){
                    val intent = Intent(this@Login, MainCompany::class.java)
                    startActivity(intent)
                }else if(user.role.equals("motoqueiro")){
                    val intent = Intent(this@Login, DeliveryListOrders::class.java)
                    startActivity(intent)
                }else if(user.role.equals("atendente")){
                    val intent = Intent(this@Login, EmployeeListOrders::class.java)
                    startActivity(intent)
                }
            }
        }
    }
    private fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){

                    enterPage(mAuth.currentUser?.uid)
                    finish()

                }else{
                    Toast.makeText(this@Login, "User does not exist", Toast.LENGTH_LONG).show()
                }
            }
    }
}