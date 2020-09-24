package com.example.dm12020foto

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    val PERMISSION_CODE = 1000;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCancelar.setOnClickListener {

            imgFoto.setImageDrawable(resources.getDrawable(R.drawable.ic_face))
        }

        btnCamera.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    val permission = arrayOf(Manifest.permission.CAMERA)
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    // a permissão já havia sido dada
                    openCamera()
                }
            } else {
                // o sistema é mais velho que o marshmallow
                openCamera()
            }
        }
    }

    private fun openCamera() {
        startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                val bitmap : Bitmap? = data?.extras?.get("data") as Bitmap?

                carregaFoto(bitmap)
            }
        }
    }

    private fun carregaFoto(bt : Bitmap?) {

        imgFoto.setImageBitmap(bt)
        //imgFoto.scaleType = ImageView.ScaleType.FIT_XY
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        // método é chamado quando a permissão é autorizada ou negada
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // a permissão foi dada
                    openCamera()

                } else {
                    // a permissão foi negada
                    Toast.makeText(this, "Permissão negada.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
