package com.example.getirlite.view.fragments.onboarding

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.getirlite.MainActivity
import com.example.getirlite.R
import com.example.getirlite.model.User
import com.example.getirlite.model.extension.AssetManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountManager @Inject constructor(): ViewModel() {
    val isLoggedIn = MutableLiveData(false)
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun start() {
        val user = auth.currentUser
        if (user != null) {
            User.userId.set(user.uid)
            isLoggedIn.value = true
        }
    }

    fun googleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(AssetManager.string(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(MainActivity.instance, gso)

        val signInIntent = googleClient.signInIntent
        MainActivity.instance.googleResult.launch(signInIntent)
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            Log.println(Log.ASSERT, "signed in", account.email.toString())
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            login(credential)
        } catch (e: ApiException) {
            e.printStackTrace()
            Log.println(Log.ASSERT, "error", e.message.toString())
            notLoggedIn()
        }
    }

    fun signInAnonymously() {
        auth.signInAnonymously().addOnCompleteListener(MainActivity.instance) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                if (user == null) {
                    notLoggedIn()
                    return@addOnCompleteListener
                }

                User.userId.set(user.uid)
                isLoggedIn.value = true
            }

        }
    }

    private fun login(credential: AuthCredential?) {
        if (credential == null) {
            notLoggedIn()
            return
        }

        auth.signInWithCredential(credential)
            .addOnCompleteListener(MainActivity.instance) { task ->
                if (!task.isSuccessful) {
                    notLoggedIn()
                    return@addOnCompleteListener
                }

                val user = auth.currentUser

                if (user == null) {
                   notLoggedIn()
                    return@addOnCompleteListener
                }

                User.userId.set(user.uid)
                User.userName.set(user.displayName ?: " ")
                User.email.set(user.email ?: "")
                User.phone.set(user.phoneNumber ?: "")
                User.didLogIn.set(true)

                isLoggedIn.value = true
            }
    }

    private fun notLoggedIn() {
        isLoggedIn.value = false
    }

    fun signOut() {
        auth.signOut()
        isLoggedIn.value = false
        User.bomb()
    }
}