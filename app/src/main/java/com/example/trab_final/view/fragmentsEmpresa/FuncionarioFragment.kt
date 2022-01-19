package com.example.trab_final.view.fragmentsEmpresa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.example.trab_final.R
import com.example.trab_final.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class FuncionarioFragment : Fragment() {

    private lateinit var addFuncionarioButton: Button
    private lateinit var emailField: EditText
    private lateinit var database: DatabaseReference
    private lateinit var _userList: ArrayList<User>
    private lateinit var auth: FirebaseAuth
    private lateinit var _user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_funcionario, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference().child("user")
        Log.v("view", view.toString())
        val companyId = auth.currentUser?.uid
        _userList = ArrayList<User>()

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshop in snapshot.children) {
                    var currentUser = postSnapshop.getValue(User::class.java)
                    _userList.add(currentUser!!)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        addFuncionarioButton = view.findViewById(R.id.btn_addFunc)
        emailField = view.findViewById(R.id.textEmail)
        Log.v("database", database.toString())

        addFuncionarioButton.setOnClickListener {
            val email = emailField.text.toString()

            for(user in _userList){
                if(user.email == email)
                    _user = user
            }
            _user.companyId = companyId
            database.child(_user.uId.toString()).setValue(_user)
        }

        return view
    }

}