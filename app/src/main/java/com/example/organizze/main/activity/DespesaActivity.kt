package com.example.organizze.main.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.organizze.R
import com.example.organizze.databinding.ActivityDespesaBinding
import com.example.organizze.main.configs.ConfigFirebase
import com.example.organizze.main.helper.Base64Custom
import com.example.organizze.main.helper.CustomData
import com.example.organizze.main.model.Movimentacao
import com.example.organizze.main.model.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class DespesaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDespesaBinding
    private val db = ConfigFirebase.getDBRef()
    private val auth = ConfigFirebase.getFbAuth()
    private var despesaTotal: Double = 0.0
    private var despesaAtt: Double = 0.0

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDespesaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewers()
        initListeners()
    }

    fun initViewers() {}

    @RequiresApi(Build.VERSION_CODES.N)
    fun initListeners() {
        binding.inputData.setText(CustomData.dataAtual())
        binding.fabDespSave.setOnClickListener { salvarDespesa() }

    }

    fun salvarDespesa() {
        if (validateFields()) {
            var movimentacao = Movimentacao(
                binding.inputData.text.toString(),
                binding.inputCatego.text.toString(),
                binding.inputDescript.text.toString(),
                "d",
                binding.resDespTxt.text.toString().toDouble()
            )
            movimentacao.saveMovi(binding.inputData.text.toString())
            recuperarDesp()
            despesaAtt = despesaTotal + binding.resDespTxt.text.toString().toDouble()
            attDespesa(despesaAtt)

        } else {
            Toast.makeText(applicationContext, "Pane no sistema!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun validateFields(): Boolean {
        if (!binding.inputData.text.toString().isEmpty()) {
            if (!binding.inputCatego.text.toString().isEmpty()) {
                if (!binding.inputDescript.text.toString().isEmpty()) {
                    if (!binding.resDespTxt.text.toString().isEmpty()) {
                        var movimentacao = Movimentacao(
                        )
                        movimentacao.saveMovi(binding.inputData.text.toString())
                        Toast.makeText(applicationContext, "Despesa salva!", Toast.LENGTH_SHORT)
                            .show()
                        return true
                    } else {
                        Toast.makeText(applicationContext, "Insira um valor", Toast.LENGTH_SHORT)
                            .show()
                        return false
                    }
                } else {
                    Toast.makeText(applicationContext, "Informe uma descrição", Toast.LENGTH_SHORT)
                        .show()
                    return false
                }
            } else {
                Toast.makeText(applicationContext, "Informe uma categoria", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        } else {
            Toast.makeText(applicationContext, "Preencha uma data", Toast.LENGTH_SHORT).show()
            return false
        }

    }

    fun recuperarDesp() {
        val email = auth.currentUser?.email.toString()
        val idUser = Base64Custom.codificar(email)
        val userRef = db.child("usuarios").child(idUser)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(Usuario::class.java)
                despesaTotal = user!!.despesaTotal
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    fun attDespesa(despesa: Double) {
        val email = auth.currentUser?.email.toString()
        val idUser = Base64Custom.codificar(email)
        db.child("usuarios").child(idUser).child("despesaTotal").setValue(despesa)
    }
}