package com.example.trab_final.models

class Orders {
    var name : String? = null
    var street : String? = null
    var district : String? = null
    var number : String? = null
    var codProduct : String? = null
    var deliveryId : String? = null
    var companyId : String? = null
    var status : String? = null

    constructor(){}

    constructor(name: String?, street: String?, district: String?, number: String?, codProduct: String?, deliveryId: String?, companyId: String?,status : String?){
        this.name = name
        this.street = street
        this.district = district
        this.number = number
        this.codProduct = codProduct
        this.deliveryId = deliveryId
        this.companyId = companyId
    }

}