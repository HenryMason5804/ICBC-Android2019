package com.henrylearns.adapterpractice.profile


import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.henrylearns.adapterpractice.R
import com.henrylearns.adapterpractice.dataobjects.FullExecObject
import com.henrylearns.adapterpractice.dataobjects.prefUtil
import kotlinx.android.synthetic.main.exec_profile_popup_layout.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_sponsor_info.view.*

//TODO change main name based on what the logged in person did

class ProfileFragment : Fragment() {
private lateinit var mPopupWindow:PopupWindow
    private lateinit var linkedInURL:String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_profile,container,false)
        mPopupWindow=PopupWindow()
        view.setOnClickListener { v->

            if (mPopupWindow.isShowing){
                mPopupWindow.dismiss()
            }

        }
            view.ICBCname.text=getUserName(context!!)

        val listener:(FullExecObject)->Unit={
            val popView=layoutInflater.inflate(R.layout.exec_profile_popup_layout,null,false)
            mPopupWindow=PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            if(Build.VERSION.SDK_INT>=21){
                mPopupWindow.setElevation(5.0f)}
        popView.popupExecView.text="${it.firstName}  ${it.lastName}"
            popView.execEmail.text=it.description
            popView.execPosition.text=it.position
            linkedInURL=it.linkedInURL
            popView.linkedInButton.setOnClickListener(){
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(linkedInURL))
                startActivity(i)
            }
            Glide.with(this).asBitmap().load(it.imageName).into(popView.circleImage)

            mPopupWindow.showAtLocation(view, Gravity.CENTER,0,20)
            mPopupWindow.isFocusable=true
        }
        val mydbr=FirebaseFirestore.getInstance().collection("ExecProfiles")
        initRecyclerAdapter(view,mydbr,listener)
        return view}


fun initRecyclerAdapter(view:View, myDbr:CollectionReference,listener:(FullExecObject)->Unit){
    val recyclerView=view.findViewById<RecyclerView>(R.id.execTeamRecycler)
    val manager = LinearLayoutManager(view.context)
    manager.orientation=LinearLayoutManager.HORIZONTAL
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = manager

    val myAdapter= profileAdapter(view.context,myDbr,listener)
    recyclerView.adapter=myAdapter
}

    override fun onPause() {
        super.onPause()
        if (mPopupWindow.isShowing){
            mPopupWindow.dismiss()
        }
    }
    fun getUserName(context: Context): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(prefUtil.USER_NAME_TAG, null)
    }
}
