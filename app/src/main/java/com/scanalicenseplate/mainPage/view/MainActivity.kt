package com.scanalicenseplate.mainPage.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.mytaskgame.application.UserApplication
import com.mytaskgame.model.entities.PlateNumber
import com.scanalicenseplate.R
import com.scanalicenseplate.application.ViewModelFactory
import com.scanalicenseplate.databinding.ActivityMainBinding
import com.scanalicenseplate.mainPage.viewModel.MainActivityViewModel
import com.scanalicenseplate.scanPage.view.ScanActivity


class MainActivity : AppCompatActivity() {


    var binding : ActivityMainBinding? = null
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var mainViewModel: MainActivityViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        val userApplication = application as UserApplication

        viewModelFactory = ViewModelFactory(userApplication.repository)
        mainViewModel = ViewModelProvider(this,viewModelFactory).get(MainActivityViewModel::class.java)

        binding?.startBtn?.setOnClickListener {

            startActivity(Intent(this,ScanActivity::class.java))

        }

        val failure = intent.getStringExtra("failure")
        if (failure == null){
            endWorkingPrptocol(intent.getStringExtra("platNumber"))
        }else{
            makefailure(failure?: "is empty")
        }


    }

    private fun makefailure(failure: String) {
        Toast.makeText(applicationContext,failure,Toast.LENGTH_LONG).show();
    }

    private fun endWorkingPrptocol(platNumber: String?) {
        if(platNumber == null){
            makefailure("is empty")
        }else{
           val number= fixplatNumber(platNumber)
            if(!checkIsrael(number)){
                makefailure("plat Number not from Israel")
            }else{
                saveData(number)
                sendData(number)
            }
        }
    }

    private fun sendData(number: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, number)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun saveData(number: String) {
        mainViewModel.insertUser(PlateNumber(number))
    }

    private fun fixplatNumber(platNumber: String): String{
        var newText = platNumber
        if (newText.contains("-")){
            newText = newText.replace("-","")
        }
        if (newText.contains(":")){
            newText = newText.replace(":","")
        }
        return newText
    }


    private fun checkIsrael(text: String) : Boolean{
        if (text.contains("IL")) return true
        return text.matches("[0-9]+".toRegex()) && text.length > 5 && text.length < 9
    }





    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}


