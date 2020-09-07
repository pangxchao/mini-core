@file:Suppress("unused")

package com.mini.plugin.util

import com.intellij.openapi.ui.InputValidator
import com.intellij.openapi.ui.Messages
import com.mini.plugin.util.Constants.TITLE_INFO

object MessageUtil {
    @JvmStatic
    @JvmOverloads
    fun showInput(message: String = "Name", value: String = "", title: String = TITLE_INFO, check: (String) -> Boolean): String {
        return Messages.showInputDialog(message, title, null, value, object : InputValidator {
            override fun checkInput(text: String): Boolean {
                return check(text)
            }

            override fun canClose(inputString: String): Boolean {
                return checkInput(inputString)
            }
        }) ?: value
    }
}
