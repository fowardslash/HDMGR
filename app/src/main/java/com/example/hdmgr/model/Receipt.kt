package com.example.hdmgr.model

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import java.util.Date
import kotlinx.parcelize.Parcelize
import java.time.DateTimeException

open class Receipt() {
    private var id: String = ""
    private var content: String = ""
    private var money: Int = 0
        set(value) {
            if (value < 0) {
                throw IllegalArgumentException("Negative money number")
            } else {
                field = value
            }
            field = value
        }
    private var customer: String = ""
    private var note: String = ""
    private var date: Date = Date()
    var dueDate: Date = Date()
    var isFinished: Boolean = false
    private val products: ArrayList<Products> = ArrayList()
    constructor(id: String) : this() {
        this.id = id
    }
    constructor(id: String, content: String, money: Int, customer: String, note: String, date: Date) : this() {
        this.id = id
        this.content = content
        this.money = money
        this.customer = customer
        this.note = note
        this.date = date
    }
    fun getId():String {
        return id
    }
    fun getContent():String {
        return content
    }
    fun getMoney():Int{
        return money
    }
    fun getCustomer():String{
        return customer
    }
    fun getNote():String{
        return note
    }
    fun getDate():Date{
        return date
    }
    fun setId(v: String){
        id = v
    }
    fun setContent(v: String): Receipt{
        content = v
        return this
    }
    fun setMoney(v: Int): Receipt{
        money = v
        return this
    }
    fun setCustomer(v: String): Receipt{
        customer = v
        return this
    }
    fun setNote(v: String): Receipt{
        note = v
        return this
    }
    fun setDate(v : Date): Receipt{
        date = v
        return this
    }

    override fun toString(): String {
        return "Receipt(id='$id', content='$content', money=$money, customer='$customer', note='$note', date=$date)"
    }
    fun addProduct(p: Products){
        products.add(p)
        var sumPrice = 0
        for(pr in products){
            sumPrice += pr.getMoney()
        }
        money = sumPrice
    }
    fun getProducts(): ArrayList<Products>{
        return products
    }

}