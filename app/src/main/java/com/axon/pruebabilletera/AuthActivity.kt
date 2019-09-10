package com.axon.pruebabilletera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_auth.*
import java.util.concurrent.TimeUnit

class AuthActivity : AppCompatActivity() {
    lateinit var phoneId :EditText
    lateinit var btnauth :Button


    private lateinit var mFirebaseAuth :FirebaseAuth
    lateinit var mPhoneAuthProvider :PhoneAuthProvider
    private lateinit var mVerificationId :String
    lateinit var mCallbacks :PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private final var TAG :String = "Auth"
    lateinit var mAuthStateListener :FirebaseAuth.AuthStateListener
    lateinit var mResendToken :PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        Log.d(TAG,intent.getStringExtra("prueba"))

        phoneId = findViewById(R.id.editTextPhone)
        btnauth = findViewById(R.id.buttonAuth)


        mFirebaseAuth =FirebaseAuth.getInstance()
        mPhoneAuthProvider = PhoneAuthProvider.getInstance()




        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:$credential")

                signInWithPhoneAuthCredential(credential)


            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)

            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")
                val i = Intent(this@AuthActivity,TokenActivity::class.java)
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId
                mResendToken = token
                i.putExtra("verificationId",mVerificationId)
                i.putExtra("token",mResendToken)
                startActivity(i)
            }

        }








        btnauth.setOnClickListener {
            var phonenumber :String = phoneId.text.toString()
            if (phonenumber.isEmpty()){
                phoneId.setError("Phone number is empty")
            }
            else{
                PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenumber,60,TimeUnit.SECONDS,this@AuthActivity,mCallbacks)
                Toast.makeText(this@AuthActivity,"Sending Message",Toast.LENGTH_SHORT).show()
            }

        }

    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential){
        mFirebaseAuth.signInWithCredential(credential).
                addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        Log.d(TAG, "signInWithCredential:success")

                        val user = task.result?.user

                    }
                    else{
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        if(task.exception is FirebaseAuthInvalidCredentialsException){

                            Log.w(TAG, "signInWithCredential:failure", task.exception)
                        }
                    }
                }
    }

    override fun onStart() {
        super.onStart()
        mAuthStateListener = FirebaseAuth.AuthStateListener {auth ->
            val user = auth.currentUser
            if(user != null){
                Toast.makeText(this@AuthActivity,"Logged in",Toast.LENGTH_SHORT).show()
                val i = Intent(this@AuthActivity,Home_Activity::class.java)
                startActivity(i)
            }
            else{

                Toast.makeText(this@AuthActivity,"nani",Toast.LENGTH_SHORT).show()
            }
        }
        mFirebaseAuth.addAuthStateListener(mAuthStateListener)
    }
}
