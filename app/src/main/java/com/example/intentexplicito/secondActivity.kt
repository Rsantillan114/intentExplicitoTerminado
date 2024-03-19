package com.example.intentexplicito

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class secondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //variable para localizar control
        val textView = findViewById<TextView>(R.id.textViewIntent)

        //variable que recibe intent
        val  bundle= intent.extras

        //crear validacion
        if (bundle!=null && bundle.getString("saludo")!=null) {

            //declarar variable que recoje el saludo
            val saludo= bundle.getString("saludo")

            //asignar el contenido de la variable
            textView.text= saludo
        }else{
            Toast.makeText(this, "El mensaje esta vacio", Toast.LENGTH_LONG).show()
        }
        // declarar variable para identificar el boton
        val buttonToThirdActivity: Button= findViewById(R.id.buttonToThirdActivity)

        //uso de la variable
        buttonToThirdActivity.setOnClickListener{
            //iniciar activity
            startActivity(this, ThirdActivity::class.java)
        }
    }

    //crear funcion con la cual incias la actividad
    fun startActivity(activity: Activity, nextActivity: Class<*>){

        //declarar varible que se le asigna el intent

        val intent= Intent(activity, nextActivity)

        //iniciamos activity
        activity.startActivity(intent)

        //finalizar activity
        activity.finish()

    }
}