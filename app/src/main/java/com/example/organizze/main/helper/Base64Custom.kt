package com.example.organizze.main.helper

import android.util.Base64

class Base64Custom {

    companion object {
        fun codificar(text: String): String {

            return Base64.encodeToString(text.toByteArray(), Base64.NO_WRAP).replace("(\\n|\\r)", "")
        }

        fun decodificar(textDecode: String): String {
            return String(Base64.decode(textDecode, Base64.NO_WRAP))
        }

    }
}