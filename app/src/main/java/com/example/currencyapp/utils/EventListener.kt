package com.example.currencyapp.utils

import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView

/**
 * An interface to manage action events of all the UI components
 */
interface EventListener {

    fun onGoButtonClick(view: View, actionId: Int, event: KeyEvent?): Boolean

    fun onButtonClick()

    fun onSelectItem(parent: AdapterView<*>?, view: View?, pos: Int, id: Long)

}