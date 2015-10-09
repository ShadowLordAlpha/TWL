/*
 * Copyright (c) 2008-2012, Matthias Mann
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Matthias Mann nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package test;

import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import de.matthiasmann.twl.CallbackWithReason;
import de.matthiasmann.twl.Container;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

/**
 *
 * @author Matthias Mann
 */
public class ModeSelectTest {

	public static void main(String... args) {
		if (GLFW.glfwInit() != GL11.GL_TRUE) {
			System.err.println("Failed To Initilize GLFW!");
			System.exit(-1);
		}
		ModeSelectTest modeSel = new ModeSelectTest();
		VideoMode mode = modeSel.selectMode();
		if (mode != null) {
			SimpleTest test = new SimpleTest();
			test.run(mode);
		}
	}

	protected GLFWVidMode desktopMode;
	protected VideoSettings.CallbackReason reason;

	public ModeSelectTest() {
		System.out.println("LWJGL Version: " + Sys.getVersion());
		desktopMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		System.out.println("Desktop mode: " + desktopMode);

		Buffer buffer = GLFW.glfwGetVideoModes(GLFW.glfwGetPrimaryMonitor());
		for (int i = 0; i < buffer.remaining(); i++) {
			System.out.println("Available mode: " + buffer.get(i).getWidth() + "x" + buffer.get(i).getHeight() + " " + buffer.get(i).getRefreshRate() +"Hz");
		}
	}

	private long window;
	
	public VideoMode selectMode() {
		try {
			if (GLFW.glfwInit() != GL11.GL_TRUE) {
				System.err.println("Failed To Initilize GLFW!");
				System.exit(-1);
			}
			window = GLFW.glfwCreateWindow(400, 300, "TWL",
					MemoryUtil.NULL, MemoryUtil.NULL);

			if (window == MemoryUtil.NULL) {
				System.err.println("Failed To Create Window!");
				System.exit(-1);
			}
			GLFW.glfwMakeContextCurrent(window);
			GL.createCapabilities();

			GLFW.glfwSwapInterval(1); // vsync

			LWJGLRenderer renderer = new LWJGLRenderer();
			renderer.setUseSWMouseCursors(true);
			GUI gui = new GUI(renderer);

			ThemeManager theme = ThemeManager.createThemeManager(
					SimpleTest.class.getResource("guiTheme.xml"), renderer);
			gui.applyTheme(theme);

			final VideoSettings settings = new VideoSettings(AppletPreferences.userNodeForPackage(VideoSettings.class),desktopMode);
			settings.setTheme("settings");
			settings.addCallback(new CallbackWithReason<VideoSettings.CallbackReason>() {
				public void callback(VideoSettings.CallbackReason aReason) {
					reason = aReason;
				}
			});
			settings.readSettings();

			Container frame = new Container();
			frame.add(settings);
			frame.setTheme("settingdialog");

			gui.getRootPane().add(frame);
			frame.adjustSize();
			frame.setPosition((gui.getWidth() - frame.getWidth()) / 2,
					(gui.getHeight() - frame.getHeight()) / 2);

			while (!(GLFW.glfwWindowShouldClose(window) == GL11.GL_TRUE) && reason == null) {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

				gui.update();
				GLFW.glfwPollEvents();
				GLFW.glfwSwapBuffers(window);
				TestUtils.reduceInputLag();
			}

			gui.destroy();
			theme.destroy();
			GLFW.glfwDestroyWindow(window);

			if (reason == VideoSettings.CallbackReason.ACCEPT) {
				settings.storeSettings();
				return settings.getSelectedVideoMode();
			}
		} catch (Exception ex) {
			TestUtils.showErrMsg(ex);
		}

		GLFW.glfwDestroyWindow(window);
		try {
			//Display.setDisplayMode(desktopMode);
		} catch (Exception ex) {
			TestUtils.showErrMsg(ex);
		}

		return null;
	}
}
