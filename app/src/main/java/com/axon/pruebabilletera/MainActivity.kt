package com.axon.pruebabilletera

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var prueba :String = "hola"
        setContentView(R.layout.activity_main)

        button.setOnClickListener{
            val intent: Intent = Intent(this, Home_Activity::class.java)
            startActivity(intent)
        }
    }
}
