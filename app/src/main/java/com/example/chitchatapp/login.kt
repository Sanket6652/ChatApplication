package com.example.chitchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chitchatapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth= FirebaseAuth.getInstance()
        binding.loginButton.setOnClickListener {
           val email=binding.email.text.toString()
            val pass=binding.password.text.toString()

            Login(email,pass)
        }
        binding.signupButton.setOnClickListener {
            val intent= Intent(this,signup::class.java)
            startActivity(intent)
        }
    }

    private fun Login(email:String,pass:String) {
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent=Intent(this@login,mainScreen::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@login,"User does not exist",Toast.LENGTH_SHORT).show()
                }
            }
    }
}