package com.example.organizze.main.model

import com.example.organizze.main.configs.ConfigFirebase
import com.example.organizze.main.helper.Base64Custom
import com.example.organizze.main.helper.CustomData
import com.google.firebase.database.DatabaseReference

class Movimentacao(
    var data: String = "",
    var categoria: String = "",
    var descricao: String = "",
    var tipo: String = "",
    var valor: Double = 0.0
) {

    fun saveMovi(data: String) {
        val a: DatabaseReference = ConfigFirebase.getDBRef()
        val email = ConfigFirebase.getFbAuth()
        var em = email.currentUser?.email.toString()
        val convert = Base64Custom.codificar(em)
        a.child("movimentacao")
            .child(convert)
            .child(this.data)
            .push()
            .setValue(this)
    }


}