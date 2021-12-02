package com.example.organizze.main.model

import com.example.organizze.main.configs.ConfigFirebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Usuario(
    var nome: String = "",
    var email: String = "",
    @get: Exclude var senha: String = "",
    @get: Exclude var idUser: String = "",
    var despesaTotal: Double = 0.0,
    var receitaTotal: Double = 0.0
)
{

    fun salvar() {
        val a: DatabaseReference = ConfigFirebase.getDBRef()
        a.child("usuarios")
            .child(this .idUser)
            .setValue(this)
    }
}
