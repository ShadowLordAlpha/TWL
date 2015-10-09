/*
 * Copyright (c) 2008-2010, Matthias Mann
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

import java.io.IOException;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.ScrollPane;
import de.matthiasmann.twl.TabbedPane;
import de.matthiasmann.twl.TextArea;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.textarea.HTMLTextAreaModel;
import de.matthiasmann.twl.theme.ThemeManager;

/**
 *
 * @author Matthias Mann
 */
public class TabbedPaneTest {

	public static void main(String[] arg) throws IOException {
		if (GLFW.glfwInit() != GL11.GL_TRUE) {
			System.err.println("Failed To Initilize GLFW!");
			System.exit(-1);
		}
		long window = GLFW.glfwCreateWindow(800, 600, "TWL TabbedPane Example", MemoryUtil.NULL, MemoryUtil.NULL);

		if (window == MemoryUtil.NULL) {
			System.err.println("Failed To Create Window!");
			System.exit(-1);
		}
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();

		GLFW.glfwSwapInterval(1); // vsync

		final TabbedPane tabbedPane = new TabbedPane();

		LWJGLRenderer renderer = new LWJGLRenderer();
		renderer.setUseSWMouseCursors(true);
		GUI gui = new GUI(tabbedPane, renderer);

		ThemeManager newTheme = ThemeManager.createThemeManager(
				SimpleTest.class.getResource("simple_demo.xml"), renderer);
		gui.applyTheme(newTheme);

		tabbedPane.addTab("Info", createInfoPane());
		tabbedPane.addTab("Image", createScrollPane());
		tabbedPane.addTab("Empty", null);

		while (!(GLFW.glfwWindowShouldClose(window) == GL11.GL_TRUE)) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

			gui.update();
			GLFW.glfwPollEvents();
			GLFW.glfwSwapBuffers(window);
		}

		GLFW.glfwDestroyWindow(window);
	}

	private static Widget createInfoPane() {
		HTMLTextAreaModel tam = new HTMLTextAreaModel();
		try {
			tam.readHTMLFromURL(GUI.class.getResource("license.html"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		TextArea ta = new TextArea(tam);
		ta.setTheme("textarea");

		ScrollPane sp = new ScrollPane(ta);
		sp.setFixed(ScrollPane.Fixed.HORIZONTAL);
		sp.setTheme("/license-scrollpane");

		return sp;
	}

	private static Widget createScrollPane() {
		Widget scrolledWidget = new Widget();
		scrolledWidget.setTheme("/scrollPaneDemoContent");

		ScrollPane scrollPane = new ScrollPane(scrolledWidget);
		scrollPane.setTheme("/scrollpane");

		return scrollPane;
	}
}
