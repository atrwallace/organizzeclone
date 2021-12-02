package com.example.organizze.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.organizze.databinding.ActivityLoginBinding
import com.example.organizze.main.configs.ConfigFirebase
import com.example.organizze.main.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var user = Usuario("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewers()
        initListeners()
    }

    fun initViewers() {

    }

    fun initListeners() {
        binding.btnEntrar.setOnClickListener { verifyAcc() }
        binding.txtNoAcc.setOnClickListener {
            startActivity(Intent(this@LoginActivity, CadastroActivity::class.java))
            finish()
        }
    }

    fun verifyAcc() {
        if (!binding.editTxtEmail.text.toString().isEmpty()) {
            if (!binding.editTxtSenha.text.toString().isEmpty()) {
                user.email = binding.editTxtEmail.text.toString()
                user.senha = binding.editTxtSenha.text.toString()
                firebasecheckAcc()
            } else {
                Toast.makeText(applicationContext, "Preencha a senha", Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            Toast.makeText(applicationContext, "Preencha o email", Toast.LENGTH_LONG)
                .show()
        }


    }

    fun firebasecheckAcc() {
        auth = ConfigFirebase.getFbAuth()
        auth.signInWithEmailAndPassword(user.email, user.senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "Login com sucesso", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@LoginActivity, PrincipalActivity::class.java))
                finish()
            } else {
                var exc = ""
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthInvalidUserException) {
                    exc = "Essa conta não existe ou foi desativada"
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    exc = "Senha ou Email inválidos"
                } catch (e: Exception) {
                    exc = "Erro: " + e.message
                    e.printStackTrace()
                }
                Toast.makeText(applicationContext, exc, Toast.LENGTH_LONG).show()
            }
        }
    }
}