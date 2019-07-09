package com.henrylearns.adapterpractice


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class SponsorInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val infoSnapshot=this.arguments
        val companyID= savedInstanceState?.get("companyID")
        Log.d("Henry","made it into SponsorInfoFragment")

        val view=inflater.inflate(R.layout.fragment_sponsor_info,container,false)
    setupContent(view)
     return view }
    private fun setupContent(view:View){
       return
    }


}
