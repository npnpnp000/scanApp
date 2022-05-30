package com.scanalicenseplate.scanPage.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.scanalicenseplate.R
import com.scanalicenseplate.databinding.ActivityScanBinding
import com.scanalicenseplate.mainPage.view.MainActivity

class ScanActivity : AppCompatActivity() {

    companion object{
        private const val CAMERA = 2
    }
    var binding : ActivityScanBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScanBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        startCamera()
    }

    private fun startCamera() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(intent, CAMERA)
            openActivityForResult()

        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                CAMERA
            )
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                recreate()
            }else{
                startActivity(Intent(this, MainActivity::class.java).putExtra("failure", "permission denied"))
                finish()
            }
        }
    }
    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    fun openActivityForResult() {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val bitmap : Bitmap = data?.extras?.get("data") as Bitmap

                recognizerStart(bitmap)
            }
        }

        resultLauncher.launch(intent)
    }

    private fun recognizerStart(bitmap: Bitmap) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = InputImage.fromBitmap(bitmap, 0)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                Log.e("visionText",visionText.text)
                startActivity(
                    Intent(this, MainActivity::class.java).putExtra(
                        "platNumber",
                        visionText.text
                    )
                )
                finish()
            }
            .addOnFailureListener { e ->
                startActivity(Intent(this, MainActivity::class.java).putExtra("failure", "Fail to recognizer the image"))
                finish()
            }
    }
}