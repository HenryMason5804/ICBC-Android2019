package com.henrylearns.adapterpractice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Layout
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.henrylearns.adapterpractice.dataobjects.prefUtil
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.exec_profile_popup_layout.*
import kotlinx.android.synthetic.main.fragment_tab_screen.*
import kotlinx.android.synthetic.main.privacy_policy_popup.view.*

class SignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var eventRef: CollectionReference = FirebaseFirestore.getInstance().collection("AccessCodes")
        setContentView(R.layout.activity_sign_in)
        Log.d("debuggingOpeningApp",intent.toString())
        if (intent.data!=null){
        Log.d("debuggingOpeningApp",intent.data.toString())}
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FirebaseSUccessToekn", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = "the token is " +token
                Log.d("FirebaseSUccessToekn", msg)
                //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })
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
            if (intent.getLongExtra("eventNotifID", -1) != null && intent.getLongExtra("eventNotifID", -1) != -1L) {
                i.putExtra("eventNotifID", intent.getLongExtra("eventNotifID", 0L))
            }
            //val thisIDRef=eventRef.whereEqualTo("code",idEdit.text.toString())

            Log.d("willow","gghostbuster")
            eventRef.addSnapshotListener{snapshot,error->
            //thisIDRef.addSnapshotListener{snapshot,error ->
                if (error!=null){
                    Log.d("SnapshotFailrue","snapfailed $error")
                }
                var correctID=false

                if (snapshot!=null){
                    for (document in snapshot) {
                        val something = document["code"]
                        if (something != null) {
                            val codeString: String = something as String
                            if (codeString.toLowerCase() == idEdit.text.toString().toLowerCase()) {
                                i.putExtra("userID", idEdit.text.toString())
                                val userName: String = nameEdit.text.toString()
                                setUserName(userName.toLowerCase(), applicationContext)
                                setUserID(idEdit.text.toString().toLowerCase(), applicationContext)
                                i.putExtra("userName", userName)
                                correctID= true
                                startActivity(i)

                            }
                        }
                    }
                    if (!correctID) {
                        Toast.makeText(applicationContext, "incorrect user ID.", Toast.LENGTH_LONG).show()
                        idEdit.setText("")


                    }
                }
                if (correctID){
                i.putExtra("userID", idEdit.text.toString())
                val userName: String = nameEdit.text.toString()
                setUserName(userName.toLowerCase(), applicationContext)
                setUserID(idEdit.text.toString().toLowerCase(), applicationContext)
                i.putExtra("userName", userName)
                startActivity(i)}
            }/*

*/
        }
        privacyButton.setOnClickListener {
            val popView = layoutInflater.inflate(R.layout.privacy_policy_popup, null, false)
            popView.setBackgroundResource(R.color.grey)
            var newTview = PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            newTview.showAtLocation(this.SignInLayout, Gravity.TOP, 0, 0)
            popView.acceptButton.setOnClickListener { newTview.dismiss() }
        }




            }

        }

fun setUserName(userName: String, context: Context) {
    val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
    editor.putString(prefUtil.USER_NAME_TAG, userName)
    editor.apply()
}


fun setUserID(userID: String, context: Context) {
    val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
    editor.putString(prefUtil.USER_ID_TAG, userID)
    editor.apply()

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