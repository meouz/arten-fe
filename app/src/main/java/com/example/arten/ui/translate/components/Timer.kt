package com.example.arten.ui.translate.components

import android.os.Handler
import android.os.Looper

class Timer(listener: OnTimerTickListener) {
    
    interface OnTimerTickListener {
        fun onTick(time: String)
    }
    
    private var handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    private var time = 0L
    private var delay = 1_000L
    
    init {
        runnable = Runnable {
            time += delay / 1_000
            handler.postDelayed(runnable!!, delay)
            listener.onTick(format())
        }
    }
    
    fun start(delay: Long) {
        handler.postDelayed(runnable!!, delay)
    }
    
    fun pause() {
        handler.removeCallbacks(runnable!!)
    }
    
    fun stop() {
        time = 0L
        handler.removeCallbacks(runnable!!)
    }
    
    private fun format(): String {
        val seconds = time
        val minutes = seconds / 60
        
        return "%02d:%02d".format(minutes, seconds % 60)
    }
}