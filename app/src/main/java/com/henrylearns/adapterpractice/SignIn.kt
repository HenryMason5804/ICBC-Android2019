package com.henrylearns.adapterpractice

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val idEdit: EditText = findViewById(R.id.idEdit)
        val idText: TextView = findViewById(R.id.idText)
        val idText2: TextView = findViewById(R.id.idText2)

        val emailEdit: EditText = findViewById(R.id.emailEdit)
        val emailText: TextView = findViewById(R.id.emailText)
        val emailText2: TextView = findViewById(R.id.emailText2)

        val nameEdit: EditText = findViewById(R.id.nameEdit)
        val nameText: TextView = findViewById(R.id.nameText)
        val nameText2: TextView = findViewById(R.id.nameText2)

        idEdit.setOnFocusChangeListener {_, b ->
                if (b){
                    idText.visibility = View.VISIBLE
                    idText2.visibility = View.VISIBLE
                } else{
                    idText.visibility = View.INVISIBLE
                    idText2.visibility = View.INVISIBLE
                }
        }

        emailEdit.setOnFocusChangeListener {_, b ->
            if (b){
                emailText.visibility = View.VISIBLE
                emailText2.visibility = View.VISIBLE
            } else{
                emailText.visibility = View.INVISIBLE
                emailText2.visibility = View.INVISIBLE
            }
        }

        nameEdit.setOnFocusChangeListener {_, b ->
            if (b){
                nameText.visibility = View.VISIBLE
                nameText2.visibility = View.VISIBLE
            } else{
                nameText.visibility = View.INVISIBLE
                nameText2.visibility = View.INVISIBLE
            }
        }
    }

}
