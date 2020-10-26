package template

import glm_.vec2.Vec2i
import glm_.vec4.Vec4
import gln.glClearColor
import gln.glViewport
import imgui.DEBUG
import imgui.ImGui
import imgui.classes.Context
import imgui.impl.gl.ImplGL3
import imgui.impl.glfw.ImplGlfw
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.glClear
import org.lwjgl.system.MemoryStack
import uno.glfw.GlfwWindow
import uno.glfw.VSync
import uno.glfw.glfw
import uno.glfw.windowHint

fun main() {
    BasicDemo()
}

private class BasicDemo {

    val clearColor = Vec4(0.45f, 0.55f, 0.6f, 1f)

    var window: GlfwWindow? = null
    var context: Context? = null
    var implGlfw: ImplGlfw? = null
    var implGl3: ImplGL3? = null
    var io: imgui.classes.IO? = null

    init {
        GLFW.glfwSetErrorCallback { error: Int, description: Long -> println("[glfwSetErrorCallback][$error][$description]") }
        glfw.init()

        windowHint.debug = DEBUG
        imgui.impl.gl.glslVersion = 130
        windowHint.context.version = "3.0"

        window = GlfwWindow(1280, 720, "BasicDemo", null, Vec2i(30), true)

        window!!.makeContextCurrent()
        GLFW.glfwSwapInterval(VSync.ON.i)
        GL.createCapabilities()

        context = Context()
        ImGui.styleColorsDark(null)

        implGlfw = ImplGlfw(window!!, true, null)
        implGl3 = ImplGL3()

        io = ImGui.io

        window!!.loop { p1: MemoryStack? -> mainLoop(p1) }

        implGlfw!!.shutdown()
        implGl3!!.shutdown()
        context!!.destroy()
        window!!.destroy()
    }

    private fun mainLoop(p1: MemoryStack?) {

        implGl3!!.newFrame()
        implGlfw!!.newFrame()

        ImGui.run {
            newFrame()
            text("Hello Basic Demo")
            render()
            glViewport(window!!.framebufferSize)
            glClearColor(clearColor)
            glClear(GL_COLOR_BUFFER_BIT)
            drawData?.let { implGl3!!.renderDrawData(it) }
        }
    }
}
