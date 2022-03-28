package com.example.parseinstagram

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseUser


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(ParseUser.getCurrentUser() != null){
            goToMainActivity()
        }
        findViewById<Button>(R.id.login).setOnClickListener{
            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            loginUser(username,password)
        }
        findViewById<Button>(R.id.signup).setOnClickListener{
            val username = findViewById<EditText>(R.id.et_username).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            signupUser(username,password)
        }


    }

    private fun signupUser(username: String,password: String){
        // Create the ParseUser
        val user = ParseUser()

// Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                // Hooray! Let them use the app now.
                goToMainActivity()
            } else {
                // Sign up didn't succeed. Look at the ParseException
                    e.printStackTrace()
                Toast.makeText(this,"Signup Unsuccessful or Username already exist",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun loginUser(username : String, password : String){
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Log.i(TAG, "Login Successful")
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this,"Login Credentials Incorrect",Toast.LENGTH_SHORT).show()
            // Signup failed.  Look at the ParseException to see what happened.
            }})
        )
    }
    private fun goToMainActivity(){
        val intent = Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object{
        const val TAG = "LoginActivity"
    }



}