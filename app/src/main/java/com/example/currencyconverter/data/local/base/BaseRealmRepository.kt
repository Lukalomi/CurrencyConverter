package com.example.currencyconverter.data.local.base

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmQuery
import io.realm.RealmResults

abstract class BaseRealmRepository<T : RealmModel>(protected val realm: Realm) {

    fun findAll(): RealmResults<T> = queryBuilder().findAll()

    fun find(id: Long): T? = queryBuilder().equalTo("id", id).findFirst()

    protected fun queryBuilder(): RealmQuery<T> = realm.where(getPersistentType())

    protected abstract fun getPersistentType(): Class<T>

}