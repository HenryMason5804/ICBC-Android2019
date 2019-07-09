package com.henrylearns.adapterpractice


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class rootFrameLayout : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_root_frame_layout, container, false)
        val transaction:FragmentTransaction=childFragmentManager.beginTransaction()
        transaction.replace(R.id.root_frame,TabScreen())
        transaction.commit()
        return view
    }


}
