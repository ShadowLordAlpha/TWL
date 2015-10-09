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
package actions;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import de.matthiasmann.twl.ActionMap.Action;
import de.matthiasmann.twl.FPSCounter;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

/**
 * A small demo showing how to use keyboard actions
 * 
 * @author Matthias Mann
 */
public class ActionDemo extends Widget {

	public static void main(String[] args) {
		try {
			if (GLFW.glfwInit() != GL11.GL_TRUE) {
				System.err.println("Failed To Initilize GLFW!");
				System.exit(-1);
			}
			long window = GLFW.glfwCreateWindow(800, 600, "TWL Action Demo",
					MemoryUtil.NULL, MemoryUtil.NULL);

			if (window == MemoryUtil.NULL) {
				System.err.println("Failed To Create Window!");
				System.exit(-1);
			}
			GLFW.glfwMakeContextCurrent(window);
			GL.createCapabilities();

			LWJGLRenderer renderer = new LWJGLRenderer();
			ActionDemo demo = new ActionDemo();
			GUI gui = new GUI(demo, renderer);

			demo.requestKeyboardFocus();

			ThemeManager theme = ThemeManager.createThemeManager(
					ActionDemo.class.getResource("actiondemo.xml"), renderer);
			gui.applyTheme(theme);

			while (!(GLFW.glfwWindowShouldClose(window) == GL11.GL_TRUE)
					&& !demo.quit) {
				GLFW.glfwPollEvents();
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

				gui.update();
				GLFW.glfwSwapBuffers(window);
				// TestUtils.reduceInputLag();
			}

			gui.destroy();
			theme.destroy();
			GLFW.glfwDestroyWindow(window);
		} catch (Exception ex) {
			// TestUtils.showErrMsg(ex);
			ex.printStackTrace();
		}
	}

	private final FPSCounter fpsCounter;
	private final Label instructions;
	private final Label result;
	public boolean quit;

	public ActionDemo() {
		fpsCounter = new FPSCounter();
		add(fpsCounter);

		instructions = new Label();
		instructions.setTheme("instructions");
		instructions
				.setText("Press the keys W, A, S, D, Ctrl-O and Ctrl-S to invoke actions");
		add(instructions);

		result = new Label();
		result.setTheme("result");
		add(result);

		getOrCreateActionMap().addMapping(this);

		setCanAcceptKeyboardFocus(true);
	}

	@Action
	public void left() {
		result.setText("You go left, but only found a wall");
	}

	@Action
	public void right() {
		result.setText("right is not a direction you want to go");
	}

	@Action
	public void forward() {
		result.setText("forward is the direction of the future");
	}

	@Action
	public void back() {
		result.setText("we have to go back into the future !");
	}

	@Action
	public void load() {
		result.setText("there is no previous save game");
	}

	@Action
	public void save() {
		result.setText("save! yes - you are save here");
	}

	@Override
	protected void layout() {
		// instructions are near the top
		instructions
				.setSize(getInnerWidth(), instructions.getPreferredHeight());
		instructions.setPosition(getInnerX(), getInnerY() + 20);

		// result is in the screen center
		result.setSize(getInnerWidth(), result.getPreferredHeight());
		result.setPosition(getInnerX(), getInnerY()
				+ (getInnerHeight() - result.getHeight()) / 2);

		// fpsCounter is bottom right
		fpsCounter.adjustSize();
		fpsCounter.setPosition(getInnerWidth() - fpsCounter.getWidth(),
				getInnerHeight() - fpsCounter.getHeight());
	}

}
