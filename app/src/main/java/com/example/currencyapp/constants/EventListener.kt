package com.example.currencyapp.constants

import android.view.KeyEvent
import android.view.View

/**
 * An interface to manage action events of all the UI components
 */
interface EventListener {

    fun onItemSelected(view: View, actionId: Int, event: KeyEvent?) : Boolean

    fun onButtonClick(view: View)



}