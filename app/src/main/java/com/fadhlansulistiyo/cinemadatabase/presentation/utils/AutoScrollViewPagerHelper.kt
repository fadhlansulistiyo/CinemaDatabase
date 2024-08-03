package com.fadhlansulistiyo.cinemadatabase.presentation.utils

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class AutoScrollViewPagerHelper(
    private val viewPager: ViewPager2,
    private val scrollInterval: Long = 3000L,
    private val scrollDuration: Long = 1000L,
    private val initialDelay: Long = 5000L
) {
    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    private var isRunning = false

    init {
        viewPager.setPageTransformer(FadePageTransformer())
    }

    fun startAutoScroll() {
        if (isRunning) return
        isRunning = true
        runnable = object : Runnable {
            override fun run() {
                val adapter = viewPager.adapter ?: return
                val itemCount = adapter.itemCount
                if (itemCount == 0) return

                val nextItem = if (viewPager.currentItem == itemCount - 1) 0 else viewPager.currentItem + 1

                if (nextItem == 0) {
                    // Delay the reset to the first item to ensure smooth transition
                    handler.postDelayed({
                        viewPager.setCurrentItem(nextItem, false)
                    }, scrollDuration)
                } else {
                    smoothScrollToPosition(viewPager, nextItem)
                }

                handler.postDelayed(this, scrollInterval)
            }
        }
        // Use the initial delay before starting the auto-scroll
        handler.postDelayed(runnable!!, initialDelay)
    }

    fun stopAutoScroll() {
        if (!isRunning) return
        isRunning = false
        handler.removeCallbacks(runnable!!)
        runnable = null
    }

    private fun smoothScrollToPosition(viewPager: ViewPager2, position: Int) {
        try {
            val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
            recyclerViewField.isAccessible = true
            val recyclerView = recyclerViewField.get(viewPager) as RecyclerView

            val smoothScroller = object : LinearSmoothScroller(viewPager.context) {
                override fun calculateTimeForScrolling(dx: Int): Int {
                    return (scrollDuration * (dx.toFloat() / recyclerView.width)).toInt()
                }
            }
            smoothScroller.targetPosition = position
            recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
        } catch (e: Exception) {
            viewPager.setCurrentItem(position, true)
        }
    }
}

class FadePageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        view.apply {
            alpha = when {
                position <= -1 || position >= 1 -> 0f
                position == 0f -> 1f
                else -> 1 - abs(position)
            }
        }
    }
}

class ZoomOutPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        view.apply {
            val scale = if (position < 0) 1 + position else 1 - position
            scaleX = scale
            scaleY = scale
            alpha = if (position < -1 || position > 1) 0f else 1f
        }
    }
}