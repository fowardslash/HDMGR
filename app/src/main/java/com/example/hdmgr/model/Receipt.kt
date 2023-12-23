package com.example.hdmgr.model

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

class Receipt {
    private var id: String = ""
    private var content: String = ""
    private var money: Int = 0
    private var customer: String = ""
    private var note: String = ""
    private var date: Date = Date()

    constructor(id: String, content: String, money: Int, customer: String, note: String, date: Date) {
        this.id = id
        this.content = content
        this.money = money
        this.customer = customer
        this.note = note
        this.date = date
    }
    constructor(id: String, content: String, money: Int, note: String) {
        this.id = id
        this.content = content
        this.money = money
        this.note = note
    }

    constructor()
    constructor(id: String, content: String, money: Int, customer: String, note: String) {
        this.id = id
        this.content = content
        this.money = money
        this.customer = customer
        this.note = note
    }

    constructor(id: String, content: String, money: Int, note: String, date: Date) {
        this.id = id
        this.content = content
        this.money = money
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
    fun setContent(v: String){
        content = v
    }
    fun setMoney(v: Int){
        money = v
    }
    fun setCustomer(v: String){
        customer = v
    }
    fun setNote(v: String){
        note = v
    }
    fun setDate(v : Date){
        date = v
    }






}