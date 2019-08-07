package com.henrylearns.adapterpractice.favourites


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.henrylearns.adapterpractice.R


class rootFrameLayout : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_root_frame_layout, container, false)
        val bund=arguments
        if (bund == null){
            childFragmentManager.beginTransaction().add(R.id.root_frame, TabScreen()).commit()
            Log.d("Henry","enteredrootFrameLayout")

        }
        else if (bund["fragType"]==1){
            childFragmentManager.beginTransaction().add(R.id.root_frame, TabScreen()).commit()
            Log.d("Henry","enteredrootFrameLayout")
        }
        else if(bund["fragType"]==2){
            val myBund=Bundle()
            myBund.putLong("ID",bund["ID"]as Long)
            val myFrag=SponsorInfoFragment()
            myFrag.arguments=myBund
            childFragmentManager.beginTransaction().add(R.id.root_frame,myFrag).commit()
        }
        else if (bund["fragType"]==3) {
            val myBund = Bundle()
            myBund.putLong("ID", bund["ID"] as Long)
            val myFrag = EventInfoFragment()
            myFrag.arguments = myBund
            childFragmentManager.beginTransaction().add(R.id.root_frame, myFrag).commit()
        }
        return view
    }

 fun doOnBackPressed():Boolean{
     if (childFragmentManager.backStackEntryCount<1) {
         return false
     }
     else
     childFragmentManager.popBackStackImmediate()
     return true
 }

    val myClickListener: (eventName: Long,fragType:Int)-> Unit = {eventID,fragType->

        val trans = childFragmentManager
            .beginTransaction()

        /*
         * IMPORTANT: We use the "root frame" defined in
         * "root_fragment.xml" as the reference to replace fragment
         */
        Log.d("Henry","root frame is ${R.id.root_frame}")
        var mybundle=Bundle()
        mybundle.putLong("ID",eventID)
        mybundle.putInt("fragType",fragType)
       val myFrag=rootFrameLayout()
        myFrag.arguments=mybundle
        trans.replace(R.id.root_frame, myFrag)
        Log.d("Henry","made it past the replace")

        /*
         * IMPORTANT: The following lines allow us to add the fragment
         * to the stack and return to it later, by pressing back
         */
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Log.d("Henry","made it past the transition")


        trans.addToBackStack("${myFrag.javaClass.toString()}")
        Log.d("Henry","made it past the backstack")

        trans.commit();
        Log.d("Henry","made it past the commit")
    }


}


