package com.example.currencyconverter.extensions

import io.realm.Realm
import io.realm.RealmObject
import io.realm.exceptions.RealmFileException

fun <R> realm(block: (realm: Realm) -> R): R {
    return getRealmDefaultInstance().use { block(it) }
}

fun realmTransaction(block: (realm: Realm) -> Unit) {
    getRealmDefaultInstance().use { it.executeTransaction { block(it) } }
}

fun realmTransactionAsync(block: (realm: Realm) -> Unit) {
    getRealmDefaultInstance().use { it.executeTransactionAsync { block(it) } }
}

fun refreshRealm() {
    realm { it.refresh() }
}

fun <R : RealmObject> R.freezeObject(): R {
    return freeze()
}

private fun getRealmDefaultInstance() : Realm {
    return try {
        Realm.getDefaultInstance()
    } catch (e: RealmFileException) {
        Realm.getDefaultConfiguration()?.let {
            Realm.deleteRealm(it)
        }
        Realm.getDefaultInstance()
    }
}
