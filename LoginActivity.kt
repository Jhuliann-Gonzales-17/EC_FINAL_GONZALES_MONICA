package com.monica.ec_final_gonzales_.view

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.monica.ec_final_gonzales_.R
import com.monica.ec_final_gonzales_.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleLauncher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val currentUser = FirebaseAuth.getInstance().currentUser
        setContentView(binding.root)
        setupViews()
        if (currentUser !=null){
            val intent = Intent( this, MainActivity::class.java )
            startActivity(intent)
            finish()
            return
        }
        //
        googleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    signInFirebaseWithGoogle(account.idToken)
                }catch (e:Exception){

                }
            }
        }

    }

    private fun setupViews(){
        binding.tilemail.editText?. addTextChangedListener {text ->
            binding.btnlogin.isEnabled = validacionCorreoPass(text.toString(), binding.tilpass.editText?.text.toString())
        }

        binding.tilpass.editText?. addTextChangedListener {text ->
            binding.btnlogin.isEnabled = validacionCorreoPass(binding.tilemail.editText?.text.toString(), text.toString())

        }

        binding.btnlogin.setOnClickListener {
            signInWithEmailPassword()
        }

        binding.btnRegister.setOnClickListener {
            signUpWithEmailPassword()
        }
    }

    private fun signInFirebaseWithGoogle(idToken: String?) {
        val authCredential = GoogleAuthProvider.getCredential(idToken,null)
        firebaseAuth.signInWithCredential(authCredential)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val user: FirebaseUser = firebaseAuth.currentUser!!

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun signUpWithEmailPassword() {
        val email = binding.tilemail.editText?.text.toString()
        val pass = binding.tilpass.editText?.text.toString()
        if(validacionCorreoPass(email,pass)){
            firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this){
                    if (it.isSuccessful){
                        Toast.makeText(this,"Usuario Creado Correctamente",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"Error, usuario no puedo ser Creado ",Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
            Toast.makeText(this,"No valido",Toast.LENGTH_SHORT).show()
        }
    }
    private fun signInWithEmailPassword() {
        val email = binding.tilemail.editText?.text.toString()
        val pass = binding.tilpass.editText?.text.toString()
        signInFirebaseWithEmail(email,pass)
    }

    private fun signInFirebaseWithEmail(email: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(email,pass)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    val user = firebaseAuth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"ERROR, Usuario no disponible",Toast.LENGTH_SHORT).show()
                }
            }
    }



    private fun signInWithGoogle() {
        val googleSignOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
          //  .requestIdToken(getString(R.string.web_client_id))
            .requestEmail().build()
        val client: GoogleSignInClient = GoogleSignIn.getClient(this,googleSignOptions)
        val intent=client.signInIntent
        googleLauncher.launch(intent)
    }


    private fun validacionCorreoPass(email:String,pass:String):Boolean{
        val validarCorreo = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val validarPass = pass.length>=6
        return validarCorreo && validarPass
    }


}