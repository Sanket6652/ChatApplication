package com.example.chitchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chitchatapp.databinding.ActivityLoginBinding
import com.example.chitchatapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {
    lateinit var binding:ActivitySignupBinding
    lateinit var mAuth: FirebaseAuth
    lateinit var dbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth= FirebaseAuth.getInstance()

        binding.signupbtn.setOnClickListener {
            val name=binding.nameET.text.toString()
            val email=binding.emailET.text.toString()
            val pass=binding.passwordET.text.toString()
            signUP(name,email,pass)

        }
    }

    private fun signUP(name:String,email: String, pass: String) {
        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                   val intent=Intent(this@signup,mainScreen::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@signup,"Error",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String?) {
       dbRef=FirebaseDatabase.getInstance().getReference()
        dbRef.child("user").child(uid!!).setValue(User(name, email, uid))
    }
}