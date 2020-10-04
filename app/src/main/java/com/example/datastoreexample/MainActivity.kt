package com.example.datastoreexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dataManager: DataManager
    private var name:String = ""
    private var github:String = ""
    private var no:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeToObservers()

        btnSave.setOnClickListener {
            val name:String = etName.text.toString()
            val github:String = etgithub.text.toString()
            val no:Int = etNumber.text.toString().toInt()

            if (name.isNotEmpty() && github.isNotEmpty() && no!=-1){
                GlobalScope.launch {
                    dataManager.storeData(name,github,no)
                }
            }else{
                Toast.makeText(this, "Enter details carefully!", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun subscribeToObservers() {

        dataManager.userNameFlow.asLiveData().observe(this) {
            name = it
            if (name.isNotEmpty()){
                tvName.visibility = View.VISIBLE
                tvName.text = "Name: $name"
            }
        }

        dataManager.userGithubFlow.asLiveData().observe(this) {
            github = it
            if (github.isNotEmpty()){
                tvGithub.visibility = View.VISIBLE
                tvGithub.text = "Github: $github"
            }
        }

        dataManager.userNo.asLiveData().observe(this) {
            no = it
            if (no != -1){
                tvNo.visibility = View.VISIBLE
                tvNo.text = "Number: $no"
            }
        }

    }
}