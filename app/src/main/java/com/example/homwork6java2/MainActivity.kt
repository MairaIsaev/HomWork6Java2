package com.example.homwork6java2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.homwork6java2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding.ivImageUri.setImageURI(uri)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListners()
    }
    private fun setupListners() = with(binding) {
        var isHasPermission = PreferenceHelper(this@MainActivity).isHasPemission
        btnGetRequest.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ){
                Log.e("permission","Разрешение есть")
                getContent.launch("image/*")
            } else{
                if (!isHasPermission) {
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                    isHasPermission = true
                }else {

                    createDialog()

                }
            }
        }
    }
    private fun createDialog(){
        AlertDialog.Builder(this)
            .setTitle("Разрешения на чтение данных")
            .setMessage("Перейти в настройки?")
            .setPositiveButton("Да, перейти"){dialog, k ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Нет, я хочу остатся"){diolog, k ->
            }
            .show()
    }
}