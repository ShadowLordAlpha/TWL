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
package bgtest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import de.matthiasmann.twl.DesktopArea;
import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.FPSCounter;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ThemeInfo;
import de.matthiasmann.twl.renderer.DynamicImage;
import de.matthiasmann.twl.renderer.Image;
import de.matthiasmann.twl.renderer.Renderer;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

/**
 *
 * @author Matthias Mann
 */
public class BackgroundTest extends DesktopArea {

	public static void main(String[] args) {
		try {
			if (GLFW.glfwInit() != GL11.GL_TRUE) {
				System.err.println("Failed To Initilize GLFW!");
				System.exit(-1);
			}
			long window = GLFW.glfwCreateWindow(800, 600,
					"TWL Background Test", MemoryUtil.NULL, MemoryUtil.NULL);

			if (window == MemoryUtil.NULL) {
				System.err.println("Failed To Create Window!");
				System.exit(-1);
			}
			GLFW.glfwMakeContextCurrent(window);
			GL.createCapabilities();

			GLFW.glfwSwapInterval(1);

			LWJGLRenderer renderer = new LWJGLRenderer();
			BackgroundTest bgtest = new BackgroundTest();
			GUI gui = new GUI(bgtest, renderer);

			ThemeManager theme = ThemeManager.createThemeManager(
					BackgroundTest.class.getResource("bgtest.xml"), renderer);
			gui.applyTheme(theme);

			while (!(GLFW.glfwWindowShouldClose(window) == GL11.GL_TRUE)) {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

				gui.update();
				GLFW.glfwSwapBuffers(window);
				GLFW.glfwPollEvents();
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
	private final Label mouseCoords;

	private Image gridBase;
	private Image gridMask;
	private DynamicImage lightImage;

	public BackgroundTest() {
		fpsCounter = new FPSCounter();
		mouseCoords = new Label();

		add(fpsCounter);
		add(mouseCoords);
	}

	@Override
	protected void layout() {
		super.layout();

		// fpsCounter is bottom right
		fpsCounter.adjustSize();
		fpsCounter.setPosition(getInnerWidth() - fpsCounter.getWidth(),
				getInnerHeight() - fpsCounter.getHeight());

		mouseCoords.adjustSize();
		mouseCoords.setPosition(0, getInnerHeight() - fpsCounter.getHeight());
	}

	@Override
	protected void applyTheme(ThemeInfo themeInfo) {
		super.applyTheme(themeInfo);
		gridBase = themeInfo.getImage("grid.base");
		gridMask = themeInfo.getImage("grid.mask");
	}

	@Override
	protected void paintBackground(GUI gui) {
		if (lightImage == null) {
			createLightImage(gui.getRenderer());
		}
		if (gridBase != null && gridMask != null) {
			int time = (int) (gui.getCurrentTime() % 2000);
			int offset = (time
					* (getInnerHeight() + 2 * lightImage.getHeight()) / 2000)
					- lightImage.getHeight();
			gridBase.draw(getAnimationState(), getInnerX(), getInnerY(),
					getInnerWidth(), getInnerHeight());
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			lightImage.draw(getAnimationState(), getInnerX(), getInnerY()
					+ offset, getInnerWidth(), lightImage.getHeight());
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			gridMask.draw(getAnimationState(), getInnerX(), getInnerY(),
					getInnerWidth(), getInnerHeight());
		}
	}

	private void createLightImage(Renderer renderer) {
		lightImage = renderer.createDynamicImage(1, 128);
		ByteBuffer bb = ByteBuffer.allocateDirect(128 * 4);
		IntBuffer ib = bb.order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
		for (int i = 0; i < 128; i++) {
			int value = (int) (255 * Math.sin(i * Math.PI / 127.0));
			ib.put(i, (value * 0x010101) | 0xFF000000);
		}
		lightImage.update(bb, DynamicImage.Format.BGRA);
	}

	@Override
	protected boolean handleEvent(Event evt) {
		if (evt.isMouseEvent()) {
			mouseCoords.setText("x: " + evt.getMouseX() + "  y: "
					+ evt.getMouseY());
		}
		return super.handleEvent(evt) || evt.isMouseEventNoWheel();
	}
}
