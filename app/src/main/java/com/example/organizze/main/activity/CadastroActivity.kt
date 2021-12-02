package com.example.organizze.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import com.example.organizze.main.configs.ConfigFirebase
import com.example.organizze.databinding.ActivityCadastroBinding
import com.example.organizze.main.helper.Base64Custom
import com.example.organizze.main.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DatabaseReference

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var usuario = Usuario("user")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewers()
        initListeners()
    }

    fun initViewers() {

    }

    fun initListeners() {
        binding.btnSendCadastro.setOnClickListener { cadastrar() }
    }

    fun cadastrar() {
        if (!binding.editTxtName.text.toString().isEmpty()) {
            if (!binding.editTxtEmail.text.toString().isEmpty()) {
                if (!binding.editTxtSenha.text.toString().isEmpty()) {

                    usuario.nome = binding.editTxtName.text.toString()
                    usuario.email = binding.editTxtEmail.text.toString()
                    usuario.senha = binding.editTxtSenha.text.toString()
                    concluirCadastro()
                } else {
                    Toast.makeText(applicationContext, "Preencha a senha", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "Preencha o email", Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(applicationContext, "Preencha o nome", Toast.LENGTH_LONG).show()
        }
    }

    fun concluirCadastro() {
        auth = ConfigFirebase.getFbAuth()
        auth.createUserWithEmailAndPassword(usuario.email, usuario.senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Usuário cadastrado com sucesso",
                        Toast.LENGTH_LONG
                    ).show()
                    val idUser = Base64Custom.codificar(usuario.email)
                    usuario.idUser = idUser
                    usuario.salvar()
                    finish()
                } else {
                    var exc = ""
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        exc = "Digite uma senha mais forte!"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        exc = "Digite um email válido!"
                    } catch (e: FirebaseAuthUserCollisionException) {
                        exc = "Essa conta já existe!"
                    } catch (e: Exception) {
                        exc = "Erro ao cadastrar usuário: " + e.message
                        e.printStackTrace()
                    }
                    Toast.makeText(applicationContext, exc, Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

}