package com.axon.pruebabilletera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class TokenActivity : AppCompatActivity() {
    lateinit var tokenid :EditText
    lateinit var btntoken :Button
    private final var TAG :String = "Token"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_token)
        Log.d(TAG,intent.getStringExtra("token"))
        Log.d(TAG,intent.getStringExtra("verificationId"))
    }
}
