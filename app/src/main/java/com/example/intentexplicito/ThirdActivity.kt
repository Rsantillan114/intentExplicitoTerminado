package com.example.intentexplicito

import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat

class ThirdActivity : AppCompatActivity() {

    private val PHONE_CODE=4000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        //variable, utilizaremos identificador para localizar boton
        val imageButonPhone: ImageButton= findViewById(R.id.imageButtonPhone)
        //variable con identificador para localizar el editText
        val editTextPhone: EditText= findViewById(R.id.editTextPhone)
        //hacer uso de variable
        imageButonPhone!!.setOnClickListener(object: View.OnClickListener{

            //crear funcion
            override fun onClick(v: View?) {
                //declaramos variable que contendra el numero capturado por el usuario
                val telefonoCapturado= editTextPhone!!.text.toString()

                //creamos la primer validacion
                if (!telefonoCapturado.isEmpty()){
                    //segunda validacion
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {

                        //invocamos funcion
                        if (checarPermiso(android.Manifest.permission.CALL_PHONE)){
                            //declarar variable para asignarle el intent
                            val intentAceptado= Intent(Intent.ACTION_CALL, Uri.parse("tel:"+telefonoCapturado))


                            //comprobar de manera explicita los permisos
                            if (ActivityCompat.checkSelfPermission(this@ThirdActivity, android.Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){

                                //detenerflujo
                                return


                            }

                            //si se otorgaron los permisos iniciar activity
                            startActivity(intentAceptado)
                        }else{
                            //en caso de no tener los permisos el usuario puede agregarlo
                            if (!shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE)){
                                requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE), PHONE_CODE)
                            }else{
                                //nos comunicaremos con el usuario para ver si desea activar los permisos
                                Toast.makeText(this@ThirdActivity,"¿Desea habilitar los permisos?", Toast.LENGTH_LONG).show()

                                //variable que recibe intent de configuraciones
                                val intentSettings= Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)

                                //USO DE LA VARIABLE
                                intentSettings.addCategory(Intent.CATEGORY_DEFAULT)
                                intentSettings.data= Uri.parse("package"+packageName)

                                //creamos las banderas para guiar al usuario
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)

                                //iniciar activity
                                startActivity(intentSettings)
                            }
                        }
                    }else{
                        //implementar la funcion para las versiones antiguas
                        versionAntigua(telefonoCapturado)
                    }


                }else{
                    Toast.makeText(this@ThirdActivity, "Deberas ingresar un numero telefonico!", Toast.LENGTH_LONG).show()
                }

            }
            })

        //declarar variable,  para asociar control
        val imageButtonWeb: ImageButton= findViewById(R.id.imageButtonWeb)

        //utilizar variable para acceso a evento
        imageButtonWeb!!.setOnClickListener{
            //declarar variable y asociarla al control

            val editTextWeb: EditText= findViewById(R.id.editTextTextWeb)

            //declarar variable que contendra cadena ingresada por usuario
            var url= editTextWeb!!.text.toString()

            //declarar la variable que recibe el intent
            val intentWeb= Intent()

            //hacer uso de la variable intent
            intentWeb.action= Intent.ACTION_VIEW
            intentWeb.data= Uri.parse("http://"+url)

            //iniciamos activity
            startActivity(intentWeb)
        }

        //declarar variable para localizar con el id el boton
        val buttonEmailMe: Button= findViewById(R.id.buttonEmailMe)

        //utilizar variable para tener acceso al evento
        buttonEmailMe!!.setOnClickListener{

            //declarar variable para alojar el correo electronico
            val email= "ramonsantillan@gmail.com"

            //declarar variable para signar intent
            val intentEmail= Intent(Intent.ACTION_SEND, Uri.parse(email))
            intentEmail.type= "plain/text"
            intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Titulo del correo electronico")
            intentEmail.putExtra(Intent.EXTRA_TEXT, "En espera de su amable respuesta")
            intentEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf("alguien@gmail.com","alguien1@gmail.com"))

            //iniciamos activity
            startActivity(Intent.createChooser(intentEmail, "elige el cliente de correo"))
        }

        //declarar variable para recibir con id el control
        val buttonContactPhone: Button= findViewById(R.id.buttonContactPhone)

        //utilizar la variable para obtener acceso al evento
        buttonContactPhone!!.setOnClickListener {
            //declarar variable a la que le asignamos el intent
            val intentCall = Intent(Intent.ACTION_DIAL, Uri.parse("tel:6624667793"))

            //inciar activity
            startActivity(intentCall)
        }

        //variable para asociar control
        val imageButtonCamara: ImageButton= findViewById(R.id.imageButtonCamara)

        //invocar el evetno
        imageButtonCamara!!.setOnClickListener{

            //declarar variable que recibe el intent
            val intentCamara= Intent("android.media.action.IMAGE_CAPTURE")

            //iniciar activity
            startActivity(intentCamara)
        }
    }
    //agregar logica de la llamada
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //estructura when
        when(requestCode){
            PHONE_CODE->{
                //Declarar 2 variables para asignar los parametros
                val permisos= permissions[0]
                val resultado= grantResults[0]

                //validar permisos
                if (permisos== android.Manifest.permission.CALL_PHONE){
                    //codigo
                    if (resultado== PackageManager.PERMISSION_GRANTED){
                        val phoneNumber: EditText= findViewById(R.id.editTextPhone)
                        phoneNumber!!.text.toString()
                        //declarar variable para alojar intent
                        val intentCall= Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber))

                        //validar permisos
                        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                            //en caso de que el programa ha rechazado el permiso
                            return
                        }
                        //en caso de que se entregara el permiso,
                        startActivity(intentCall)
                    }else{
                        //informar al usuario de que los permisos fueron rechazados
                        Toast.makeText(this, "ha denegado el permiso", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    //funcion para validar permiso otorgado por el usuario//
        fun checarPermiso(permission: String):Boolean{

        //variable que aloja resultado
        val result= this.checkCallingOrSelfPermission(permission)

        //retornar valor
        return result == PackageManager.PERMISSION_GRANTED
    }

    //funcion que evalua versiones antiguas
    fun versionAntigua(phoneNumber: String){

        //variable que recibe intent
        val intentCall= Intent(Intent.ACTION_CALL, Uri.parse("tel: "+phoneNumber))

        // realizar una validacion
        if (checarPermiso(android.Manifest.permission.CALL_PHONE)){

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED ){
                return
            }
            startActivity(intentCall)

        }

    }
}