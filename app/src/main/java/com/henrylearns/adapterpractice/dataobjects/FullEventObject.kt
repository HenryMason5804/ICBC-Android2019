package com.henrylearns.adapterpractice.dataobjects

import java.util.Date
import java.sql.Time

data class FullEventObject (val id:Long=0,
                            val assocSponsors:(ArrayList<Long>)=ArrayList<Long>(),
                            val location:String="",//This I think might need to have the name of the place and co-ordinates has the stuff that is used in google maps
                            val about:String="",
                            val description:String="",
                            val eventCode:String = "",
                            val image:String="",
                            val name:String="",
                            val time: Date =Date(0),
                            val timeEnd:Date = Date(0),
                            val eventType:String="",
                            val favouritesOnly:Boolean=false
//val co-ordinates: Not sure how this would be typed though so for now I will skip it

)