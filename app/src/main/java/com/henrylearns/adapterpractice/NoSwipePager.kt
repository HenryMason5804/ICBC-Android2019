package com.henrylearns.adapterpractice

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoSwipePager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    private var localEnabled = true

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (this.localEnabled) {
            super.onTouchEvent(event)
        } else false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (this.localEnabled) {
            super.onInterceptTouchEvent(event)
        } else false
    }

    fun setPagingEnabled(enabled: Boolean) {
        this.localEnabled = enabled
    }
}