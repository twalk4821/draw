package com.reactlibrary

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import java.util.HashMap

class CanvasView : SimpleViewManager<CanvasViewInternal>() {
    override fun getName(): String {
        return "CanvasView"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): CanvasViewInternal {
        return CanvasViewInternal(reactContext)
    }

    override fun getCommandsMap(): Map<String, Int>? {
        val map = HashMap<String, Int>()
        map["tap"] = 0
        return map
    }

    override fun receiveCommand(view: CanvasViewInternal, commandType: Int, args: ReadableArray?) {
        when (commandType) {
            0 -> {
                view.onTap()
                return
            }
            else -> throw IllegalArgumentException(String.format(
                    "Unsupported command %d received by %s.",
                    commandType,
                    javaClass.simpleName))
        }
    }
}