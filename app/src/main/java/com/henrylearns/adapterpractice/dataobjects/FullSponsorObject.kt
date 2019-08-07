package com.henrylearns.adapterpractice.dataobjects

data class FullSponsorObject (val id:Long=0
                              ,val about:String=""
                              ,val assocEvents:(ArrayList<Long>)=ArrayList<Long>(),
                              val companyWebsite:String="",
                              val description:String="",
                              val image:String="",
                              val introduction:String="",
                              val linkedIn:String="",
                              val name:String="",
                              val sponsorLevel:String="")