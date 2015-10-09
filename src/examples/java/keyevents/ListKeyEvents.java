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
package keyevents;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import de.matthiasmann.twl.Container;
import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.ScrollPane;
import de.matthiasmann.twl.ScrollPane.Fixed;
import de.matthiasmann.twl.TextArea;
import de.matthiasmann.twl.input.lwjgl.Keyboard;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.textarea.SimpleTextAreaModel;
import de.matthiasmann.twl.theme.ThemeManager;
import test.TestUtils;

/**
 *
 * @author Matthias Mann
 */
public class ListKeyEvents extends Container {

	public static void main(String[] args) {
		try {
			if (GLFW.glfwInit() != GL11.GL_TRUE) {
				System.err.println("Failed To Initilize GLFW!");
				System.exit(-1);
			}
			long window = GLFW.glfwCreateWindow(800, 600, "TWL Keyboard Event Debugger",
					MemoryUtil.NULL, MemoryUtil.NULL);

			if (window == MemoryUtil.NULL) {
				System.err.println("Failed To Create Window!");
				System.exit(-1);
			}
			GLFW.glfwMakeContextCurrent(window);
			GL.createCapabilities();

			GLFW.glfwSwapInterval(1); // vsync

			LWJGLRenderer renderer = new LWJGLRenderer();
			ListKeyEvents demo = new ListKeyEvents();
			GUI gui = new GUI(demo, renderer);

			ThemeManager theme = ThemeManager.createThemeManager(
					ListKeyEvents.class.getResource("ListKeyEvents.xml"),
					renderer);
			gui.applyTheme(theme);

			while (!(GLFW.glfwWindowShouldClose(window) == GL11.GL_TRUE) && !demo.quit) {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

				demo.listKeyEvents();
				gui.update();
				GLFW.glfwPollEvents();
				GLFW.glfwSwapBuffers(window);
			}

			gui.destroy();
			theme.destroy();
			GLFW.glfwDestroyWindow(window);
		} catch (Exception ex) {
			TestUtils.showErrMsg(ex);
		}
	}

	private final StringBuilder sb;
	private final SimpleTextAreaModel textAreaModel;
	private final TextArea textArea;
	private final ScrollPane scrollPane;

	public boolean quit;

	public ListKeyEvents() {
		sb = new StringBuilder();
		sb.append("Press any key - the keyboard events are displayed below\n");

		textAreaModel = new SimpleTextAreaModel(sb.toString());
		textArea = new TextArea(textAreaModel);
		scrollPane = new ScrollPane(textArea);
		scrollPane.setFixed(Fixed.HORIZONTAL);

		add(scrollPane);
	}

	// TODO i need to switch this to use callbacks
	public void listKeyEvents() {
		boolean hadEvents = false;
		while (Keyboard.next()) {
			if (Character.toString(Keyboard.getEventCharacter()).equals(Character.toString(Event.CHAR_NONE))) {
				sb.append(String.format("%s %s (code %d) char %c (%d)\n",
						Keyboard.getEventKeyState() ? "PRESSED " : "RELEASED",
								Character.toString(Keyboard.getEventCharacter()),
						Keyboard.getEventKey(), Keyboard.getEventCharacter(),
						(int) Keyboard.getEventCharacter()));
			} else {
				sb.append(String.format("%s %s (code %d)\n",
						Keyboard.getEventKeyState() ? "PRESSED " : "RELEASED",
						Character.toString(Keyboard.getEventCharacter()),
						Keyboard.getEventKey()));
			}
			hadEvents = true;
		}
		if (hadEvents) {
			boolean atEnd = scrollPane.getScrollPositionY() == scrollPane
					.getMaxScrollPosY();
			textAreaModel.setText(sb.toString());
			if (atEnd) {
				scrollPane.validateLayout();
				scrollPane.setScrollPositionY(scrollPane.getMaxScrollPosY());
			}
		}
	}

}
