package com.example.intentexplicito

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    //declarar variable para el saludo
    val SALUDO= "Saludo desde el activity principal"
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //declaramos variables
        val buttonCalcular: Button= findViewById(R.id.buttonCalcular)
        val buttonSiguiente: Button= findViewById(R.id.buttonSiguiente)
        val editTextEdad: EditText= findViewById(R.id.editTextEdad)
        val TextViewEdad: TextView= findViewById(R.id.textViewEdad)

        //evento boton siguiente
        buttonSiguiente.setOnClickListener {
            //invocamos la fucion
            startActivity(this, secondActivity::class.java)
        }
        //evento boton calcular
        buttonCalcular.setOnClickListener {
            //declaramos las variables
            val nacimiento: Int= editTextEdad.text.toString().toInt()
            //variable para el año actual
            val actual: Int= Calendar.getInstance().get(Calendar.YEAR)
            //declaramos la variable, en donde realizamos el calculo
            val edad: Int= actual-nacimiento

            //mostrar resultado
            TextViewEdad.text= "Tu tienes: $edad, años"
        }

        }

    //declarar funcion para solicitar siguiente activity
    fun startActivity(activity: Activity, nextActivity: Class<*>){

        //variable que recibe el intent
        val intent= Intent(activity, nextActivity)

        //hacer uso de variable que tiene el activity
        intent.putExtra("saludo", SALUDO)

        //iniciar la actividad
        activity.startActivity(intent)

        //finalizar intent
        activity.finish()
    }

    }