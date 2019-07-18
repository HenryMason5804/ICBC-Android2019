package com.henrylearns.adapterpractice

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.abs


object DayModel {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DayItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DayItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDayItem(i))
        }
        ITEMS.sortBy { it.start }

    }

    private fun addItem(item: DayItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.name, item)
    }

    private fun createDayItem(position: Int): DayItem {
        val start = Calendar.getInstance()
        start.add(Calendar.MINUTE, Random().nextInt(100))
        val end = start.clone() as Calendar
        end.add(Calendar.MINUTE, 30)
        return DayItem(start, end, makeDetails(position), "The gym or something")
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Keynote pre-show: ").append(position)
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DayItem(
        val start: Calendar,
        val end: Calendar,
        val name: String,
        val location: String,
        val tags: ArrayList<String>? = null
    ) {
        private val duration_in_millis = abs(start.timeInMillis - end.timeInMillis)
        val am_pm = if(start.get(Calendar.AM_PM)== Calendar.AM) "AM" else "PM"
        private val hours = if(start.get(Calendar.HOUR) == 0 ) 12 else start.get(Calendar.HOUR)
        private val minutes = start.get(Calendar.MINUTE)
        val duration = (if (duration_in_millis > MILS_IN_HOUR)
            if (duration_in_millis > MILS_IN_HOUR * 2)
                (duration_in_millis / (1000 * 60 * 60)).toString() + " hours"
            else ((duration_in_millis / (1000 * 60 * 60)).toString() + " hour")
        else ((duration_in_millis / (1000 * 60)) % 60).toString() + " minutes")
        override fun toString(): String = name
        fun getTime(): String {
            return when {
                minutes == 0 -> if(hours <10) "0$hours" else "$hours"
                minutes < 10 -> "$hours:0$minutes"
                else -> "$hours:$minutes"
            }
        }


        companion object {
            const val MILS_IN_HOUR: Int = 3600000
        }
    }
}