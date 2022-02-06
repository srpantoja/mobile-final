package com.example.trab_final.models

class Company {
    private var name : String? = null
    private var email : String? = null
    private var role : String? = null
    private var uId : String? = null
    private var orders : ArrayList<Orders>? = null
    private var users : ArrayList<User>? = null

    constructor(){}

    constructor(name: String?, email: String?, role: String?, uId: String?){
        this.name = name
        this.email = email
        this.role = role
        this.uId = uId
    }

    constructor(name: String?, email: String?, role: String?, uId: String?, orders: ArrayList<Orders>?, users : ArrayList<User>?){
        this.name = name
        this.email = email
        this.role = role
        this.uId = uId
        this.orders = orders
        this.users = users
    }
}