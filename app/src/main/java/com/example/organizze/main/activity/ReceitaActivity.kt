package com.example.organizze.main.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.organizze.R
import com.example.organizze.databinding.ActivityReceitaBinding
import com.example.organizze.main.configs.ConfigFirebase
import com.example.organizze.main.helper.Base64Custom
import com.example.organizze.main.helper.CustomData
import com.example.organizze.main.model.Movimentacao
import com.example.organizze.main.model.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ReceitaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceitaBinding
    private val db = ConfigFirebase.getDBRef()
    private val auth = ConfigFirebase.getFbAuth()
    private var receitaTotal: Double = 0.0
    private var receitaAtt: Double = 0.0

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceitaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewers()
        initListeners()
    }

    fun initViewers() {}

    @RequiresApi(Build.VERSION_CODES.N)
    fun initListeners() {
        binding.inputData.setText(CustomData.dataAtual())
        binding.fabDespSave.setOnClickListener { saveReceita() }
    }

    fun saveReceita() {
        if (validateField()) {
            val movimentacao = Movimentacao(
                binding.inputData.text.toString(),
                binding.inputCate.text.toString(),
                binding.inputDescrip.text.toString(),
                "r",
                binding.resDespTxt.text.toString().toDouble()
            )
            movimentacao.saveMovi(binding.inputData.text.toString())
            recuperarReceita()
            receitaAtt = receitaTotal + binding.resDespTxt.text.toString().toDouble()
            attRec(receitaAtt)
        } else {
            Toast.makeText(applicationContext, "erro no sistema", Toast.LENGTH_SHORT).show()
        }

    }

    fun validateField(): Boolean {
        if (!binding.inputData.text.toString().isEmpty()) {
            if (!binding.inputCate.text.toString().isEmpty()) {
                if (!binding.inputDescrip.text.toString().isEmpty()) {
                    if (!binding.resDespTxt.text.toString().isEmpty()) {
                        val movimentacao = Movimentacao()
                        movimentacao.saveMovi(binding.inputData.text.toString())

                        Toast.makeText(applicationContext, "Receita salva!", Toast.LENGTH_SHORT)
                            .show()
                        return true
                    } else {
                        Toast.makeText(applicationContext, "Preencha um valor", Toast.LENGTH_SHORT)
                            .show()
                        return false
                    }
                } else {
                   toast("jesus")
                    return false
                }
            } else {
                Toast.makeText(applicationContext, "Informe uma categoria!", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        } else {
            Toast.makeText(applicationContext, "Preencha a data", Toast.LENGTH_SHORT)
                .show()
            return false
        }
    }

    fun recuperarReceita() {
        val email = auth.currentUser?.email.toString()
        val converter = Base64Custom.codificar(email)
        val ref = db.child("usuarios").child(converter)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(Usuario::class.java)
                receitaTotal = user!!.receitaTotal
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun attRec(receita: Double) {
        val email = auth.currentUser?.email.toString()
        val converter = Base64Custom.codificar(email)
        db.child("usuarios").child(converter).child("receitaTotal").setValue(receita)
    }
    fun toast(a: String){
        Toast.makeText(this, a, Toast.LENGTH_SHORT).show()

    }
}