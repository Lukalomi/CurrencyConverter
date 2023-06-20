package com.example.currencyconverter.data.local.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.UUID


open class Currency : RealmObject() {
    @PrimaryKey
    open var id: String = UUID.randomUUID().toString()
    open var name: String = ""
    open var amount: Int = 0
}
