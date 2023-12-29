package com.example.hdmgr.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.hdmgr.model.Receipt
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

class DB(context: Context?) : SQLiteOpenHelper(context, "dbQLHD.db", null, 2) {
    lateinit var db: SQLiteDatabase
    companion object{
        const val CREATE_RECEIPT: String = "CREATE TABLE Receipt (" +
                "id Integer PRIMARY KEY NOT NULL UNIQUE," +
                "content Nvarchar(1000) NOT NULL," +
                "price Bigint NOT NULL," +
                "customer Nvarchar(1000) NOT NULL," +
                "note Nvarchar(1000) NOT NULL," +
                "date Date NOT NULL," +
                "dueDate Date," +
                "isFinished Tinyint NOT NULL DEFAULT 0" +
                ")"
        const val TABLE: String = "Receipt"
        const val UPDATE_CMD = "ALTER TABLE Receipt ADD dueDate Date"

        const val UPDATE_CMD1 = "ALTER TABLE Receipt ADD COLUMN isFinished Tinyint NOT NULL DEFAULT 0"
    }

    override fun onCreate(myDB: SQLiteDatabase) {
        Log.d("Db", "Db created")
        myDB.execSQL(CREATE_RECEIPT)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(UPDATE_CMD)
        p0?.execSQL(UPDATE_CMD1)
    }
    fun getReceipt(sortingType: String = "date desc"): Cursor{
        db = readableDatabase
        return db.query(TABLE, null, null, null, null, null, sortingType)
    }
    fun addReceipt(r: Receipt){
        db = writableDatabase
        val contentValues: ContentValues = ContentValues()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val calendar: Calendar = Calendar.getInstance()
        contentValues.put("content", r.getContent())
        contentValues.put("price", r.getMoney())
        contentValues.put("customer", r.getCustomer())
        contentValues.put("note", r.getNote())
        contentValues.put("date", formatter.format(LocalDateTime.ofInstant(calendar.toInstant(), calendar.timeZone.toZoneId()).toLocalDate()))
        db.insert(TABLE, null, contentValues)
    }
    fun deleteReceipt(id: Int   ){
        db = writableDatabase
        db.delete(TABLE, "id = ?", arrayOf(id.toString()))
    }
}