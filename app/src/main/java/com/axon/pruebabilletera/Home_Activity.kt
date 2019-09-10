package com.axon.pruebabilletera

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class Home_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_)

        val str_qr = HashMap<String, Any>()

        str_qr.put("Nombre", "Angel Manuel")
        str_qr.put("Apellido", "Wayar Encinas")
        str_qr.put("Telefono", "3498533")
        str_qr.put("Correo", "angelmanuelwayar@gmail.com")

        Log.i("datos", str_qr.toString())

    }
}
