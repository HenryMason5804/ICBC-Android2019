package com.henrylearns.adapterpractice.favourites


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.henrylearns.adapterpractice.R
import kotlinx.android.synthetic.main.fragment_sponsor_info.view.*


class SponsorInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_sponsor_info,container,false)
        Glide.with(view).asBitmap().load("https://www.pixoto.com/images-photography/animals/other/butteflies-5742287486189568.jpg").into(view.imageView)
        view.companyName.setText("Company Name")
        view.introduction.setText("Company Introduction")
        view.aboutParagraph.setText("This paragraph will describe the company")
        view.linkedInButton.setText(" linkedIn")
        view.companyButton.setText("company Website")
     return view }

//val infoSnapshot=this.arguments
//        val companyID= savedInstanceState?.get("companyID")
//        Log.d("Henry","made it into SponsorInfoFragment")
    // setupContent(view)
///private fun setupContent(view:View){
//       return
//    }
}
