package com.drink.juicefilter

import android.accessibilityservice.AccessibilityService
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JuiceFilterService: AccessibilityService() {
    private var scope = CoroutineScope(Dispatchers.Default)
    private var showWarning = true
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        val volume  = event?.keyCode == KeyEvent.KEYCODE_VOLUME_UP || event?.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN

        if(!volume && showWarning) {
            showWarning = false
            Toast.makeText(this, "Some app/device is trying to send data to your to phone, Check your usb port, recently installed app", Toast.LENGTH_LONG).show()
            waitForWarning()
        }
        return !volume
    }

    private fun waitForWarning(){
        scope.launch {
            delay(30*1000)
            showWarning = true
        }
    }

}