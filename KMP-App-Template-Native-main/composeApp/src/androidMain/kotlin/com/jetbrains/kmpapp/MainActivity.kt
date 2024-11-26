package com.jetbrains.kmpapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.semantics.text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Remove when https://issuetracker.google.com/issues/364713509 is fixed
            LaunchedEffect(isSystemInDarkTheme()) {
                enableEdgeToEdge()
            }
            App(


                            private val authHelper = FirebaseAuthHelper()

                            override fun onCreate(savedInstanceState: Bundle?) {
                                super.onCreate(savedInstanceState)
                                setContentView(R.layout.activity_main)

                                buttonLogin.setOnClickListener {
                                    val email = editTextEmail.text.toString()
                                    val password = editTextPassword.text.toString()

                                    CoroutineScope(Dispatchers.Main).launch {
                                        val success = authHelper.signInWithEmailAndPassword(email, password)
                                        if (success) {
                                            Toast.makeText(this@MainActivity, "Login successful.", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(this@MainActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        }
            )
        }
    }

