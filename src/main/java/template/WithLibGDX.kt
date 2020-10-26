package template

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics
import glm_.vec4.Vec4
import gln.glClearColor
import gln.glViewport
import imgui.ImGui
import imgui.classes.Context
import imgui.impl.gl.ImplGL3
import imgui.impl.glfw.ImplGlfw
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import uno.glfw.GlfwWindow

fun main() {
    DesktopLauncher()
}

private class DesktopLauncher {
    init {
        val config = Lwjgl3ApplicationConfiguration()
        Lwjgl3Application(MyGdxGame(), config)
    }
}

private class MyGdxGame : ApplicationAdapter() {

    private val clearColor = Vec4(0.45f, 0.55f, 0.6f, 1f)

    private var lwjglGlfw: ImplGlfw? = null
    private var implgl3: ImplGL3? = null
    private var ctx: Context? = null
    private var window: GlfwWindow? = null
    private var windowHandle: Long = 0

    override fun create() {
        GL.createCapabilities()
        ctx = Context()
        windowHandle = (Gdx.graphics as Lwjgl3Graphics).window.windowHandle
        window = GlfwWindow.from(windowHandle);
        window!!.makeContextCurrent()
        lwjglGlfw = ImplGlfw(window!!, true, null)
        implgl3 = ImplGL3()
        ImGui.styleColorsDark(null)
    }

    override fun render() {

        implgl3!!.newFrame()
        lwjglGlfw!!.newFrame()

        ImGui.run {
            newFrame()
            text("Hello, LibGDX")
            render()
            glViewport(window!!.framebufferSize)
            glClearColor(clearColor)
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)
            drawData?.let { implgl3!!.renderDrawData(it) }
        }
    }

    override fun dispose() {
        lwjglGlfw!!.shutdown()
        ctx!!.shutdown()
    }
}
