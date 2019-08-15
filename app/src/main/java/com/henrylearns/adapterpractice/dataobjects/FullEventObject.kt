package com.henrylearns.adapterpractice.dataobjects

import java.util.Date
import java.sql.Time

data class FullEventObject (val id:Long=0,
                            val associatedSponsors:(ArrayList<Long>)=ArrayList<Long>(),
                            val location:String="",//This I think might need to have the name of the place and co-ordinates has the stuff that is used in google maps
                            val about:String="",
                            val description:String="",
                            val eventCode:String? = "",
                            val image:String="",
                            val title:String="",
                            val startDate: Date =Date(0),
                            val endDate:Date = Date(0),
                            val eventType:String="",
                            val scheduleOnly:Boolean=false
//val co-ordinates: Not sure how this would be typed though so for now I will skip it

)