package com.example.organizze.main.configs

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class ConfigFirebase {

    companion object {
        fun getFbAuth(): FirebaseAuth {

            return FirebaseAuth.getInstance()
        }

        fun getDBRef(): DatabaseReference {

            return FirebaseDatabase.getInstance().reference
        }
    }

}