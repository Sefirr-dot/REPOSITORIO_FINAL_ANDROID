package example

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseAuthHelper {
    private val auth: FirebaseAuth = Firebase.auth

    suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            // Handle exceptions (e.g., invalid credentials)
            false
        }
    }

    // ... other authentication functions (e.g., sign up, sign out)
}