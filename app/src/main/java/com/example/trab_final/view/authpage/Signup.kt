package com.example.trab_final.view.authpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.trab_final.R
import com.example.trab_final.models.Company
import com.example.trab_final.models.User
import com.example.trab_final.view.company.MainCompany
import com.example.trab_final.view.deliveryman.DeliveryListOrders
import com.example.trab_final.view.employee.EmployeeListOrders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Signup : AppCompatActivity() {

    private lateinit var inputName : EditText
    private lateinit var inputEmail : EditText
    private lateinit var inputPassword : EditText
    private lateinit var inputRole : Spinner
    private lateinit var signupBtn : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        inputName = findViewById(R.id.edit_name)
        inputEmail = findViewById(R.id.edit_email)
        inputPassword = findViewById(R.id.edit_password)
        inputRole = findViewById(R.id.spinner)
        signupBtn = findViewById(R.id.btn_signup)
        mAuth = FirebaseAuth.getInstance()

        val roles = listOf("motoqueiro", "atendente", "empresa")

        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_list_item_1, roles)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_item)

        inputRole.adapter = adapterSpinner
        signupBtn.setOnClickListener {
            val name = inputName.text.toString()
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()
            val role = inputRole.selectedItem.toString()
            signup(name, email, role, password)
        }
    }
    private fun signup(name: String, email: String, role: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    if(role == "empresa"){
                        addUserToDatabase(name, email, role, mAuth.currentUser?.uid!!)
                        addCompanyToDatabase(name, email, role, mAuth.currentUser?.uid!!)
                    }
                    else
                        addUserToDatabase(name, email, role, mAuth.currentUser?.uid!!)
                    if(role == "empresa"){
                        val intent = Intent(this@Signup, MainCompany::class.java)
                        startActivity(intent)
                    }else if(role == "motoqueiro"){
                        val intent = Intent(this@Signup, DeliveryListOrders::class.java)
                        startActivity(intent)
                    }else if(role == "atendente"){
                        val intent = Intent(this@Signup, EmployeeListOrders::class.java)
                        startActivity(intent)
                    }
                    finish()
                }else{
                    Toast.makeText(this@Signup, "some error occurred", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun addCompanyToDatabase(name: String, email: String, role: String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("company").child(uid).setValue(Company(name, email,role, uid))
    }

    private fun addUserToDatabase(name: String, email: String, role: String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("user").child(uid).setValue(User(name, email,role, uid, ""))
    }
}