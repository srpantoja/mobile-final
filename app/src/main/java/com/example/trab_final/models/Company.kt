package com.example.trab_final.models

class Company {
    var name : String? = null
    var email : String? = null
    var role : String? = null
    var uId : String? = null
    var orders : ArrayList<Orders>? = null
    var users : ArrayList<User>? = null

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