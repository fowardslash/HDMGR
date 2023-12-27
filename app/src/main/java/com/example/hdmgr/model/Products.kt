package com.example.hdmgr.model

class Products(
    private var id: String,
    private var name: String,
    private var quantity: Int,
    private var money: Int
) {
    fun getId() : String {
        return id
    }
    fun getName() : String {
        return name
    }
    fun getQuantity() : Int {
        return quantity
    }
    fun getMoney() : Int {
        return money
    }
    fun setId(id: String) {
        this.id = id
    }
    fun setName(n: String): Boolean{
        if(n.length < 3){
            return false
        }
        name = n
        return true
    }
    fun setQuantity(q: Int): Boolean {
        if(q < 0){
            return false
        }
        quantity = q
        return true
    }
    fun setMoney(m: Int): Boolean{
        if(m < 0){
            return false
        }
        money = m
        return true
    }
}