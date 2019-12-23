package com.reactlibrary

import android.graphics.Outline
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import androidx.core.view.ViewCompat
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import java.util.HashMap
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.annotations.ReactProp

class CanvasView : SimpleViewManager<CanvasViewInternal>() {

    sealed class Command(val name: String, val signal: Int) {
        object PATH_START: Command("PATH_START", 0)
        object PATH_MOVE: Command("PATH_MOVE", 1)
        object PATH_END: Command("PATH_END", 2)
        object CREATE_DROP: Command("CREATE_DROP", 3)
    }

    override fun getName(): String {
        return "CanvasView"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): CanvasViewInternal {
        return CanvasViewInternal(reactContext)
    }

    override fun getCommandsMap(): Map<String, Int>? =
        HashMap<String, Int>().apply {
            put(Command.PATH_START.name, Command.PATH_START.signal)
            put(Command.PATH_MOVE.name, Command.PATH_MOVE.signal)
            put(Command.PATH_END.name, Command.PATH_END.signal)
            put(Command.CREATE_DROP.name, Command.CREATE_DROP.signal)
        }

    override fun receiveCommand(view: CanvasViewInternal, commandType: Int, args: ReadableArray?) {

        when (commandType) {
            Command.PATH_START.signal -> {
                view.startPath()
            }
            Command.PATH_MOVE.signal -> {
                args?.getMap(0)?.toHashMap()?.let {
                    val x = (it.get("x") as Double).toFloat()
                    val y = (it.get("y") as Double).toFloat()

                    view.addPoint(x, y)
                }
            }
            Command.PATH_END.signal -> {
                view.endPath()
            }
            Command.CREATE_DROP.signal -> {
                args?.getMap(0)?.toHashMap()?.let {
                    val x = (it.get("x") as Double).toFloat()
                    val y = (it.get("y") as Double).toFloat()

                    view.createDrop(x, y)
                }
            }
            else -> throw IllegalArgumentException(String.format(
                    "Unsupported command %d received by %s.",
                    commandType,
                    javaClass.simpleName))
        }
    }
}