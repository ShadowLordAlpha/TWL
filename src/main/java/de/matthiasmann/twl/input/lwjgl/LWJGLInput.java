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
package de.matthiasmann.twl.input.lwjgl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.input.Input;

/**
 * Input handling based on LWJGL's Mouse & Keyboard classes.
 *
 * @author Matthias Mann
 */
public class LWJGLInput implements Input {

	private boolean wasActive;

	public boolean pollInput(GUI gui) {
		boolean active = ((GLFW.glfwGetWindowAttrib(
				GLFW.glfwGetCurrentContext(), GLFW.GLFW_VISIBLE) == GL11.GL_TRUE) && (GLFW
				.glfwGetWindowAttrib(GLFW.glfwGetCurrentContext(),
						GLFW.GLFW_FOCUSED) == GL11.GL_TRUE)) ? true : false;
		if (wasActive && !active) {
			wasActive = false;
			return false;
		}
		wasActive = active;

		if (Keyboard.isCreated()) {
			while (Keyboard.next()) {
				gui.handleKey(Keyboard.getEventKey(), Keyboard.getEventCharacter(), Keyboard.getEventKeyState());
			}
		}
		if (Mouse.isCreated()) {
			while (Mouse.next()) {
				gui.handleMouse(Mouse.getEventX(), Mouse.getEventY(), Mouse.getEventButton(), Mouse.getEventButtonState());

				int wheelDelta = Mouse.getEventDWheel();
				if (wheelDelta != 0) {
					gui.handleMouseWheel(wheelDelta / 120);
				}
			}
		}
		return true;
	}

}
