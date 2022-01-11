package com.example.trab_final.models

class User {
    var name : String? = null
    var email : String? = null
    var role : String? = null
    var uId : String? = null

    constructor(){}

    constructor(name: String?, email: String?, role: String?, uId: String?){
        this.name = name
        this.email = email
        this.role = role
        this.uId = uId
    }


}