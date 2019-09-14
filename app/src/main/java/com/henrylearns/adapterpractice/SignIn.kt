package com.henrylearns.adapterpractice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.henrylearns.adapterpractice.dataobjects.prefUtil

class SignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val idEdit: EditText = findViewById(R.id.idEdit)
        val idText: TextView = findViewById(R.id.idText)
        val idText2: TextView = findViewById(R.id.idText2)
/*
        val emailEdit: EditText = findViewById(R.id.emailEdit)
        val emailText: TextView = findViewById(R.id.emailText)
        val emailText2: TextView = findViewById(R.id.emailText2)
*/
        val nameEdit: EditText = findViewById(R.id.nameEdit)
        val nameText: TextView = findViewById(R.id.nameText)
        val nameText2: TextView = findViewById(R.id.nameText2)

        idEdit.setOnFocusChangeListener { _, b ->
            if (b) {
                idText.visibility = View.VISIBLE
                idText2.visibility = View.VISIBLE
            } else {
                idText.visibility = View.INVISIBLE
                idText2.visibility = View.INVISIBLE
            }
        }

      /*  emailEdit.setOnFocusChangeListener { _, b ->
            if (b) {
                emailText.visibility = View.VISIBLE
                emailText2.visibility = View.VISIBLE
            } else {
                emailText.visibility = View.INVISIBLE
                emailText2.visibility = View.INVISIBLE
            }
        }
*/
        nameEdit.setOnFocusChangeListener { _, b ->
            if (b) {
                nameText.visibility = View.VISIBLE
                nameText2.visibility = View.VISIBLE
            } else {
                nameText.visibility = View.INVISIBLE
                nameText2.visibility = View.INVISIBLE
            }
        }

        val nextButton = findViewById<Button>(R.id.submitID)
        nextButton.setOnClickListener {
            var i = Intent(this, MainActivity::class.java)
            if( intent.getLongExtra("eventNotifID",-1)!=null && intent.getLongExtra("eventNotifID",-1) != -1L){
                i.putExtra("eventNotifID",intent.getLongExtra("eventNotifID",0L))
            }
            i.putExtra("userID", idEdit.text)
            val userName: String = nameEdit.text.toString()
            setUserName(userName, applicationContext)
            i.putExtra("userName", userName)
            startActivity(i)

        }
    }


    fun setUserName(userName: String, context: Context) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putString(prefUtil.USER_NAME_TAG, userName)
        editor.apply()
    }

    fun getUserID(context: Context): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(prefUtil.USER_ID_TAG, null)
    }

    fun setUserID(picUrl: String, context: Context) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putString(prefUtil.USER_ID_TAG, picUrl)
        editor.apply()


    }
}
/*
    <TextView
            android:id="@+id/emailText"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Email"
            android:layout_marginLeft="30dp"
            android:textColor="@color/trademarkRed"
            android:visibility="invisible"
    />
    <EditText
        android:imeOptions="actionGo"
        android:singleLine="true"
            android:id="@+id/emailEdit"
            android:layout_width="325dp"
            android:layout_height="50dp"
            android:textSize="20dp"
            android:paddingLeft="15dp"
            android:hint="myemail@email.com"
            android:layout_gravity="center"
            android:maxLength="20"
            android:outlineProvider="none"
            android:background="@drawable/round_edit_text"/>
    <TextView
            android:id="@+id/emailText2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Please enter your Email here"
            android:layout_marginLeft="30dp"
            android:textColor="@color/trademarkRed"
            android:textSize="10dp"
            android:visibility="invisible"
    />
 */