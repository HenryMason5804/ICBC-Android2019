package com.henrylearns.adapterpractice


import android.os.Bundle
import android.util.Log
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
        childFragmentManager.beginTransaction().add(R.id.root_frame,TabScreen()).commit()
        return view
    }
    val myClickListener: (eventName: String)-> Unit = {
        val trans = childFragmentManager
            .beginTransaction()
        /*
         * IMPORTANT: We use the "root frame" defined in
         * "root_fragment.xml" as the reference to replace fragment
         */
        Log.d("Henry","root frame is ${R.id.root_frame}")
        trans.replace(R.id.root_frame, SponsorInfoFragment())
        Log.d("Henry","made it past the replace")

        /*
         * IMPORTANT: The following lines allow us to add the fragment
         * to the stack and return to it later, by pressing back
         */
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Log.d("Henry","made it past the transition")

        trans.addToBackStack(null)
        Log.d("Henry","made it past the backstack")

        trans.commit();
        Log.d("Henry","made it past the commit")
    }


}
