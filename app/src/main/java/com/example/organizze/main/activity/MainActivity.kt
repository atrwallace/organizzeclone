package com.example.organizze.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.organizze.databinding.ActivityMainBinding
import com.example.organizze.main.configs.ConfigFirebase
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var verify: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewers()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        verififyUserLogged()
    }
    fun initViewers() {}
    fun initListeners() {
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this@MainActivity, CadastroActivity::class.java))
        }
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
    }

    fun verififyUserLogged() {
        verify = ConfigFirebase.getFbAuth()
        verify.signOut()
        if (verify.currentUser != null) {
            startActivity(Intent(this@MainActivity, PrincipalActivity::class.java))
        } else {
        }
    }


}